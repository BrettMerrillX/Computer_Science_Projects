#include <stdio.h>
#include <stdlib.h>
#include <sink.h>

sink::sink()
{
	InputExists = false;
	Input2Exists = false;
}

void sink::SetInput(Image * img)
{
	Input = img;
	InputExists = true;

}
void sink::SetInput2(Image * img)
{
	Input2 = img;
	Input2Exists = true;
}


const Image *sink::GetInput() const
{
	return Input;
}
const Image *sink::GetInput2() const
{
	return Input2;
}