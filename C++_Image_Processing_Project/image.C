#include <stdio.h>
#include <stdlib.h>
#include <image.h>
#include <source.h>
#include <filters.h>

Image::Image()
{
	width = 0;
	height = 0;
	isUpdated = false;
	pixel = NULL;
	sourceX =NULL;
	
}
Image::Image(int w, int h)
{
	width = w;
	height = h;
	isUpdated = false;
	pixel = new Pixel[w*h]; 
}

Image::Image(Image &img)
{
	img.resetSize(width, height);
	
}
Image::~Image()
{
	if(pixel != NULL){
		delete [] pixel;
	}
}		



void Image::resetSize(int w, int h)
	{
		pixel = new Pixel[w*h];
		width = w;
		height = h;

	}
int Image::GetWidth() const
{
	return width;
}

void Image::SetWidth(int w)
{
	width = w;
}
int Image::GetHeight() const
{
	return height;
}
void Image::SetHeight(int h)
{
	height = h;
}
Pixel *Image::GetPixel() const
{
	return pixel;
}
void Image::Update() const
{
	sourceX->UpdateSource(isUpdated);

}
void Image::SetFilter(source * filter)
{
	sourceX = filter;
}
