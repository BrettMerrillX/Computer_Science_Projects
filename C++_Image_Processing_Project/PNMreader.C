#include <PNMreader.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>


PNMreader::PNMreader(char *input)
{
	filename =  new char[128];
	strcpy(filename,input);
	img.SetFilter(this);
}

PNMreader::~PNMreader()
{
	delete [] filename;
}

void PNMreader::Execute()
{
	readImage(filename, *GetOutput());
}
const char *PNMreader::SourceName()
{
	const char *Name = "PNMreader";
	return Name;
}
void PNMreader::readImage(char *filename, Image &output)
{
	char magicNum[128];
	int width, height, maxval;
	Image *img;
	FILE *f_in;
	f_in = fopen(filename, "r");
 	fscanf(f_in, "%s\n%d %d\n%d\n", magicNum, &width, &height, &maxval);  
	output.resetSize(width, height);
	fread(output.GetPixel(), sizeof(Pixel), width * height, f_in);
	fclose(f_in);
}
void PNMreader::Update()
{
	char msg[128];	sprintf(msg, "%s: about to execute", SourceName());
	Logger::LogEvent(msg);	Execute();	sprintf(msg, "%s: done executing", SourceName()); 
	Logger::LogEvent(msg);
}
