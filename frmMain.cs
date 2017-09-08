using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Microsoft.VisualBasic.FileIO;

namespace SDP {
    public partial class frmMain : Form {
        public frmMain() {
            InitializeComponent();
            CreateStatusBar();
        }

        protected StatusBar mainStatusBar = new StatusBar();
        protected StatusBarPanel statusPanel = new StatusBarPanel();
        protected StatusBarPanel datetimePanel = new StatusBarPanel();

        //Brett - to keep track of Column names For Database and file:
        private string[] fields;
        private string[] fieldsBackup;
        private string[] dbFields = new string[14] { "FirstName", "LastName", "Email", "Phone", "IP", "Address1", "Address2", "City", "State", "Zip", "FeedDate", "Gender", "Misc", "Misc2" };
        

        private void CreateStatusBar() {
            // Set first panel properties and add to StatusBar
            statusPanel.BorderStyle = StatusBarPanelBorderStyle.Sunken;
            statusPanel.Text = "Application started. No action yet.";
            statusPanel.ToolTipText = "Last Activity";
            statusPanel.AutoSize = StatusBarPanelAutoSize.Spring;
            mainStatusBar.Panels.Add(statusPanel);

            // Set second panel properties and add to StatusBar
            datetimePanel.BorderStyle = StatusBarPanelBorderStyle.Raised;
            datetimePanel.ToolTipText = "DateTime: " + System.DateTime.Today.ToString();
            datetimePanel.Text = System.DateTime.Today.ToLongDateString();
            datetimePanel.AutoSize = StatusBarPanelAutoSize.Contents;
            mainStatusBar.Panels.Add(datetimePanel);

            mainStatusBar.ShowPanels = true;
            // Add StatusBar to Form controls
            this.Controls.Add(mainStatusBar);

        }

        private void btnChooseFolder_Click(object sender, EventArgs e) {
            using (var fbd = new FolderBrowserDialog()) {
                DialogResult result = fbd.ShowDialog();

                if (result == DialogResult.OK && !string.IsNullOrWhiteSpace(fbd.SelectedPath)) {
                    string[] files = Directory.GetFiles(fbd.SelectedPath);

                    lblChosenFolder.Text = fbd.SelectedPath;
                    lstFiles.Items.Clear();
                    foreach (var file in files) {
                        lstFiles.Items.Add(file);
                    }

                }
            }
        }

        private void btnShowHeader_Click(object sender, EventArgs e) {
            try
                {
                if (lstFiles.SelectedItem.ToString().Length > 0) {
                    var path = lstFiles.SelectedItem.ToString();
                    using (TextFieldParser csvParser = new TextFieldParser(path)) {
                        //csvParser.CommentTokens = new string[] { "#" };
                        csvParser.SetDelimiters(new string[] { "," });
                        csvParser.HasFieldsEnclosedInQuotes = chkHasFieldsEnclosedInQuotes.Checked;

                        //// Read the row with the column names
                        //string[] fields = csvParser.ReadFields();
                        //
                        //lstHeader.Items.Clear();
                        //lstHeader.MultiColumn = false;
                        //lstHeader.Items.AddRange(fields);


                        //========================================
                        //Brett's edit

                        // Populates the two boxes labeled lstHeader and fileHeader with 
                        // the items in the array dbFeilds and fields respectively. 

                        fields = csvParser.ReadFields();
                        fieldsBackup = new string[fields.Length];
                        fields.CopyTo(fieldsBackup, 0);
                        lstHeader.Items.Clear();
                        lstHeader.MultiColumn = false;
                        lstHeader.Items.AddRange(dbFields);

                        fileHeader.Items.Clear();
                        fileHeader.MultiColumn = false;
                        fileHeader.Items.AddRange(fields);
                        // Done
                        //========================================


                    }
                }
            }catch
            {
                statusPanel.Text = "No files to show.";
            }
            
        }

        private void btnSplit_Click(object sender, EventArgs e) {
            var files = lstFiles.SelectedItems.Cast<string>().ToList();
            foreach(var file in files) {
                var ret = SplitFile(file.ToString());
            }
        }

        private int SplitFile(string file) {
            statusPanel.Text = @"Processing: " + file;
            datetimePanel.Text = System.DateTime.Today.ToLongDateString();
            var path = Path.GetDirectoryName(file);
            var fileName = Path.GetFileNameWithoutExtension(file);
            var extension = Path.GetExtension(file);
            var linesPerFile = int.Parse(txtLinesPerFile.Text);
            var maxFiles = int.Parse(txtMaxFiles.Text);

            using (StreamReader sr = new StreamReader(file)) {

                // Skip the row with the column names
                string header = "";
                int outCount = 1;
                int lineCount = 1;
                StreamWriter sw = new StreamWriter(Path.Combine(path, fileName + "_" + outCount.ToString() + extension));
                statusPanel.Text = @"Created: " + Path.Combine(path, fileName + "_" + outCount.ToString() + extension);
                datetimePanel.Text = System.DateTime.Today.ToLongDateString();
                if (chkKeepHeader.Checked) {
                    header = sr.ReadLine();
                    sw.WriteLine(header);
                    lineCount = 0;
                }
                while (!sr.EndOfStream) {
                    // Read current line fields, pointer moves to the next line.
                    sw.WriteLine(sr.ReadLine());
                    lineCount++;
                    if (lineCount % 1000 == 0) {
                        statusPanel.Text = @"Progress: " + file + @" " + lineCount.ToString();
                        datetimePanel.Text = System.DateTime.Today.ToLongDateString();
                    }
                    if (lineCount == linesPerFile) {
                        sw.Close();
                        lineCount = 0;
                        outCount++;

                        if (outCount > maxFiles) {
                            statusPanel.Text = @"Max files reached.";
                            datetimePanel.Text = System.DateTime.Today.ToLongDateString();
                            break;
                        }
                        sw = new StreamWriter(Path.Combine(path, fileName + "_" + outCount.ToString() + extension));
                        if (chkKeepHeader.Checked) {
                            sw.WriteLine(header);
                        }
                        statusPanel.Text = @"Created: " + Path.Combine(path, fileName + "_" + outCount.ToString() + extension);
                        datetimePanel.Text = System.DateTime.Today.ToLongDateString();
                    }
                }
                sr.Close();
                sw.Close();
            }
            statusPanel.Text = @"Split Files complete.";
            datetimePanel.Text = System.DateTime.Today.ToLongDateString();
            return 0;
        }

        private void btnMakeImportFile_Click(object sender, EventArgs e) {
            var files = lstFiles.SelectedItems.Cast<string>().ToList();
            foreach (var file in files) {
                statusPanel.Text = @"Processing: " + file;
                datetimePanel.Text = System.DateTime.Today.ToLongDateString();
                var path = Path.GetDirectoryName(file);
                statusPanel.Text = path.ToString();
                var fileName = Path.GetFileNameWithoutExtension(file);
                var extension = Path.GetExtension(file);
                var header = "LOAD DATA LOCAL INFILE\n";
                //========================================
                //Brett's edits
                //adds @ to the begining of the file fields that don't exist in the database
                int i = 0;
                for (i = 0; i < fields.Length; i++)
                {
                    int check = 0;
                    foreach (string dbItem in dbFields)
                    {
                        if (fields[i] == dbItem)
                        {
                            check = 1;
                        }
                    }
                    if (check == 0)
                    {
                        fields[i] = "@" + fields[i]; ;
                    }
                }                
                var FileFeilds = string.Join(",", fields);



                var contents = header + '"' + file.Replace(@"\",@"/") + "\"\n" + "INTO TABLE data_load.feed\n" + "FIELDS TERMINATED BY \',\'\n" + "OPTIONALLY ENCLOSED BY '\"'\n" +
                    "LINES TERMINATED BY '\\r\\n'\n" + "(" + FileFeilds +")\n" + "SET `FeedType` = 'BIZOPP';\n\n";
                using (var sw = new StreamWriter(path + @"\b2b_auto_load.sql", true)) {
                    sw.Write(contents);
                }
                //Done.
                //========================================
            }
            statusPanel.Text = @"Make Import Files complete.";
            
            datetimePanel.Text = System.DateTime.Today.ToLongDateString();
        }

        

        private void chkSelectAll_CheckedChanged(object sender, EventArgs e) {
            for (int i = 0; i < lstFiles.Items.Count; i++) {
                lstFiles.SetSelected(i, chkSelectAll.Checked);
            }
        }

        private void btnUnZip_Click(object sender, EventArgs e) {
            var files = lstFiles.SelectedItems.Cast<string>().ToList();
            foreach (var file in files) {
                statusPanel.Text = @"Unzipping: " + file;
                datetimePanel.Text = System.DateTime.Today.ToLongDateString();
                var path = Path.GetDirectoryName(file);
                var fileName = Path.GetFileNameWithoutExtension(file);
                var extension = Path.GetExtension(file);

                ZipFile.ExtractToDirectory(file, path);
            }
            statusPanel.Text = @"Unzip complete.";
            datetimePanel.Text = System.DateTime.Today.ToLongDateString();
        }

        //========================================
        //Brett's edit
        
        
        private void btnRenameColumn_Click(object sender, EventArgs e)
        // Uses the selcted items from the text boxes named lstHeader and fileHeader
        // and changes the item from fileHeader to match the item from lstHeader.
        // Then updates the text box.
        {
            var fileHeaderChoice = fileHeader.Text.ToString();
            var dbHeaderChoice = lstHeader.Text.ToString();
            int i = 0;
            for (i = 0; i < fields.Length;i++)
            {
                if (fields[i] == fileHeaderChoice)
                {
                    fields[i] = dbHeaderChoice;
                }
            }
            fileHeader.Items.Clear();
            fileHeader.MultiColumn = false;
            fileHeader.Items.AddRange(fields);
            
        }


        
        private void btnRevertToOriginal_Click(object sender, EventArgs e)
        // Uses the fieldsBackup array to change the fields array back to the original
        // Then updates the text box.
        {
            statusPanel.Text = fieldsBackup[0].ToString();
            fields = fieldsBackup;
            fileHeader.Items.Clear();
            fileHeader.MultiColumn = false;
            fileHeader.Items.AddRange(fields);
        }
        //Done.
        //========================================
       
    }
}

