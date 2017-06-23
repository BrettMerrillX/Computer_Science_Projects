#include <PNMwriter.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

void PNMwriter::Write(char * filename)
{
	WriteImage(filename, GetInput());
}

const char *PNMwriter::SinkName()
{
	const char *Name = "PNMwriter";
	return Name;
}

void PNMwriter::WriteImage(char *filename, const Image *img)
{
	FILE *f_out;
	f_out = fopen(filename, "w");
	fprintf(f_out, "P6\n");
	fprintf(f_out, "%d %d\n", img->GetWidth(), img->GetHeight());
	fprintf(f_out, "255\n");
	fwrite(img->GetPixel(), 3 * img->GetWidth(), img->GetHeight(), f_out);
	fclose(f_out);
}
