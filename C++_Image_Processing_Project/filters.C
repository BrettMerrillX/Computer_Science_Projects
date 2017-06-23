#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <filters.h>
#include <image.h>

Shrinker::Shrinker()
{
	img.SetFilter(this);
}	

void Shrinker::Execute()
{
	HalveInSize(GetInput(), *GetOutput());
}
void Shrinker::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
}
const char *Shrinker::FilterName()
{
	const char *Name = "Shrinker";
	return Name;
}

void Shrinker::HalveInSize(const Image *input, Image &output)
{
	int input_height = input->GetHeight();
	int input_width = input->GetWidth();
	int output_height = input_height/2;
	int output_width = input_width/2;
	output.resetSize(output_width, output_height);
	int i,j;
	int rc = 0;
	for(i = 0; i < output_height;i++)
	{
		for(j = 0; j< output_width;j++)
		{
 			output.GetPixel()[j + output.GetWidth()*i] = input->GetPixel()[j*2 + input_width*2*i];
		}		
	
	}
	


}
LRConcat::LRConcat()
{
	img.SetFilter(this);
}	

void LRConcat::Execute()
{
	if(!Input2Exists)
	{
		char msg[128];
		sprintf(msg, "%s: No input2",FilterName());
		DataFlowException e(FilterName(), msg);
		throw e;
	}else{
		LeftRightConcatenate(GetInput(),GetInput2(),*GetOutput());
	}
}
void LRConcat::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	if(!InputExists)
	{
		char msg[128];
		sprintf(msg, "%s: No input2",FilterName());
		DataFlowException e(FilterName(), msg);
		throw e;
	}
	GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
	sprintf(msg, "%s: about to update input2", SourceName());
	Logger::LogEvent(msg);
	if(!Input2Exists)
	{
		char msg[128];
		sprintf(msg, "%s: No input2",FilterName());
		DataFlowException e(FilterName(), msg);
		throw e;
	}
	GetInput2()->Update();
	sprintf(msg, "%s: done updating input2", SourceName());
	Logger::LogEvent(msg);
	
}

const char *LRConcat::FilterName()
{
	const char *Name = "LRConcat";
	return Name;
}


void LRConcat::LeftRightConcatenate(const Image *leftInput,const Image *rightinput, Image &output)
{
	int leftHeight = leftInput->GetHeight();
	int leftWidth = leftInput->GetWidth();
	int rightHeight = rightinput->GetHeight();
	int rightWidth = rightinput->GetWidth();
	if(leftHeight != rightHeight)
	{	
		char msg[128];
		sprintf(msg, "%s: heights must match: %d, %d",FilterName(),leftHeight,rightHeight);
		DataFlowException e(FilterName(), msg);
		throw e;
	}else{

		int output_height = leftInput->GetHeight();
		int output_width = leftInput->GetWidth() + rightinput->GetWidth();
		output.resetSize(output_width, output_height);
		int i,j;
		int rc = 0;
		for(i = 0; i < output_height;i++)
		{
			for(j = 0; j< output_width;j++)
			{
				if(j<output_width-rightWidth)
				{
 					output.GetPixel()[j + output.GetWidth()*i] = leftInput->GetPixel()[j + leftWidth*i];
				}else{
					output.GetPixel()[j + output.GetWidth()*i] = rightinput->GetPixel()[j - leftWidth +rightWidth*i];
				}
			}
		}
	}

}
TBConcat::TBConcat()
{
	img.SetFilter(this);
}	
void TBConcat::Execute()
{
	TopBottomConcatenate(GetInput(), GetInput2(), *GetOutput());
}

void TBConcat::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
	sprintf(msg, "%s: about to update input2", SourceName());
	Logger::LogEvent(msg);
	GetInput2()->Update();
	sprintf(msg, "%s: done updating input2", SourceName());
	Logger::LogEvent(msg);

}
const char *TBConcat::FilterName()
{
	const char *Name = "TBConcat";
	return Name;
}

void TBConcat::TopBottomConcatenate(const Image *topInput, const Image *bottomInput, Image &output)
{
	int topHeight = topInput->GetHeight();
	int topWidth = topInput->GetWidth();
	int bottomHeight = bottomInput->GetHeight();
	int bottomWidth = bottomInput->GetWidth();
	int output_height = topInput->GetHeight() + bottomInput->GetHeight();
	int output_width = bottomInput->GetWidth();
	output.resetSize(output_width, output_height);
	int i,j;
	for(i = 0; i < output_height;i++)
	{
		for(j = 0; j< output_width;j++)
		{
			if(i<topHeight)
			{
 				output.GetPixel()[j + output.GetWidth()*i] = topInput->GetPixel()[j + topWidth*i];
			}else{
				output.GetPixel()[j + output.GetWidth()*i] = bottomInput->GetPixel()[j + bottomWidth*(i -topHeight)];
			}
		}
	
	}



}
Blender::Blender()
{
	img.SetFilter(this);
}	


void Blender::SetFactor(float ratio)
{
	factorNum = ratio;
}

void Blender::Execute()
{
	Blend(GetInput(), GetInput2(), factorNum, *GetOutput());
}
const char *Blender::FilterName()
{
	const char *Name = "Blender";
	return Name;
}
void Blender::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
	sprintf(msg, "%s: about to update input2", SourceName());
	Logger::LogEvent(msg);
	GetInput2()->Update();
	sprintf(msg, "%s: done updating input2", SourceName());
	Logger::LogEvent(msg);

}

void Blender::Blend(const Image *input1, const Image *input2, float factor, Image &output)
{	
	if(factor>1 || factor<0)
		{
		char msg[128];
		sprintf(msg, "Invalid factor for Blender: %f",factor);
		DataFlowException e(FilterName(), msg);
		throw e;
	}else
		{
		output.resetSize(input1->GetWidth(), input1->GetHeight());
		
		for(int i=0; i < input1->GetWidth()* input1->GetHeight();i++){
		output.GetPixel()[i].r = factor*input1->GetPixel()[i].r + (1-factor)*input2->GetPixel()[i].r;
			output.GetPixel()[i].g = factor*input1->GetPixel()[i].g + (1-factor)*input2->GetPixel()[i].g;
			output.GetPixel()[i].b = factor*input1->GetPixel()[i].b + (1-factor)*input2->GetPixel()[i].b;
		}	
	}

}

Mirror::Mirror()
{
	img.SetFilter(this);
}	

void Mirror::Execute()
{
	MirrorImage(GetInput(), *GetOutput());
}
void Mirror::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
}
const char *Mirror::FilterName()
{
	const char *Name = "Mirror";
	return Name;
}

void Mirror::MirrorImage(const Image *input, Image &output)
{
	int input_height = input->GetHeight();
	int input_width = input->GetWidth();
	int output_height = input_height;
	int output_width = input_width;
	output.resetSize(output_width, output_height);
	int i,j,k;
	int rc = 0;
	for(i = 0; i < output_height;i++)
	{
		k = output_width;
		for(j = 0; j< output_width;j++)
		{
 			output.GetPixel()[k + output_width*i] = input->GetPixel()[j + input_width*i];
			k--;
		}		
	
	}
}

Rotate::Rotate()
{
	img.SetFilter(this);
}	

void Rotate::Execute()
{
	RotateImage(GetInput(), *GetOutput());
}
void Rotate::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
}
const char *Rotate::FilterName()
{
	const char *Name = "Rotate";
	return Name;
}

void Rotate::RotateImage(const Image *input, Image &output)
{
	int input_height = input->GetHeight();
	int input_width = input->GetWidth();
	int output_height = input_width;
	int output_width = input_height;
	output.resetSize(output_width, output_height);
	int i,j,k;
	int rc = 0;
	for(i = 0; i < input_width;i++)
	{
		k = output_width-1;
		for(j = 0; j< input_height;j++)
		{
 			output.GetPixel()[k + output_width*i] = input->GetPixel()[i + input_width*j];
			k--;
		}		
	
	}
}

Subtract::Subtract()
{
	img.SetFilter(this);
}	

void Subtract::Execute()
{
	SubtractImage(GetInput(), GetInput2(), *GetOutput());
}
void Subtract::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
}
const char *Subtract::FilterName()
{
	const char *Name = "Subtract";
	return Name;
}

void Subtract::SubtractImage(const Image *input,const Image *input2, Image &output)
{
	
	int input_height = input->GetHeight();
	int input_width = input->GetWidth();
	int input2_height = input2->GetHeight();
	int input2_width = input2->GetWidth();
	int output_height = input_height;
	int output_width = input_width;
	output.resetSize(output_width, output_height);
	int i,j,k;
	int rc = 0;
	for(i = 0; i < input_height;i++)
	{
		for(j = 0; j< input_width;j++)
		{
			
			if(input->GetPixel()[j + input_width*i].r > input2->GetPixel()[j + input2_width*i].r){
				output.GetPixel()[j + output_width*i].r =  input->GetPixel()[j + input_width*i].r - input2->GetPixel()[j + input2_width*i].r;
			}else{
				output.GetPixel()[j + output_width*i].r = 0;
			}
			if(input->GetPixel()[j + input_width*i].g > input2->GetPixel()[j + input2_width*i].g){
				output.GetPixel()[j + output_width*i].g =  input->GetPixel()[j + input_width*i].g - input2->GetPixel()[j + input2_width*i].g;
			}else{
				output.GetPixel()[j + output_width*i].g = 0;
			}
			if(input->GetPixel()[j + input_width*i].b > input2->GetPixel()[j + input2_width*i].b){
				output.GetPixel()[j + output_width*i].b =  input->GetPixel()[j + input_width*i].b - input2->GetPixel()[j + input2_width*i].b;
			}else{
				output.GetPixel()[j + output_width*i].b = 0;
			}
		}		
	
	}
}

Blur::Blur()
{
	img.SetFilter(this);
}	

void Blur::Execute()
{
	BlurImage(GetInput(), *GetOutput());
}
void Blur::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
}
const char *Blur::FilterName()
{
	const char *Name = "Blur";
	return Name;
}

void Blur::BlurImage(const Image *input, Image &output)
{
	int input_height = input->GetHeight();
	int input_width = input->GetWidth();
	int output_height = input_height;
	int output_width = input_width;
	output.resetSize(output_width, output_height);
	int i,j,k;
	int rc = 0;
	for(i = 0; i < output_height;i++)
	{
		for(j = 0; j< output_width;j++)
		{
			if(i == 0 || i== output_height-1 || j ==0 || j == output_width-1){
 				output.GetPixel()[j + output_width*i] = input->GetPixel()[j + input_width*i];
			}else{
				output.GetPixel()[j + output_width*i].r = input->GetPixel()[j - 1 + input_width*(i-1)].r/8 + input->GetPixel()[j + input_width*(i-1)].r/8 + input->GetPixel()[j + 1 + input_width*(i-1)].r/8 + input->GetPixel()[j - 1 + input_width*i].r/8 + input->GetPixel()[j + 1 + input_width*i].r/8 + input->GetPixel()[j - 1 + input_width*(i+1)].r/8 + input->GetPixel()[j + input_width*(i+1)].r/8 + input->GetPixel()[j + 1 + input_width*(i+1)].r/8 ; 
				output.GetPixel()[j + output_width*i].g = input->GetPixel()[j - 1 + input_width*(i-1)].g/8 + input->GetPixel()[j + input_width*(i-1)].g/8 + input->GetPixel()[j + 1 + input_width*(i-1)].g/8 + input->GetPixel()[j - 1 + input_width*i].g/8 + input->GetPixel()[j + 1 + input_width*i].g/8 + input->GetPixel()[j - 1 + input_width*(i+1)].g/8 + input->GetPixel()[j + input_width*(i+1)].g/8 + input->GetPixel()[j + 1 + input_width*(i+1)].g/8 ;
				output.GetPixel()[j + output_width*i].b = input->GetPixel()[j - 1 + input_width*(i-1)].b/8 + input->GetPixel()[j + input_width*(i-1)].b/8 + input->GetPixel()[j + 1 + input_width*(i-1)].b/8 + input->GetPixel()[j - 1 + input_width*i].b/8 + input->GetPixel()[j + 1 + input_width*i].b/8 + input->GetPixel()[j - 1 + input_width*(i+1)].b/8 + input->GetPixel()[j + input_width*(i+1)].b/8 + input->GetPixel()[j + 1 + input_width*(i+1)].b/8 ; 
			 
			
			}
		}		
	
	}
}

Grayscale::Grayscale()
{
	img.SetFilter(this);
}	

void Grayscale::Execute()
{
	GrayscaleImage(GetInput(), *GetOutput());
}
void Grayscale::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
}
const char *Grayscale::FilterName()
{
	const char *Name = "Grayscale";
	return Name;
}

void Grayscale::GrayscaleImage(const Image *input, Image &output)
{
	int input_height = input->GetHeight();
	int input_width = input->GetWidth();
	int output_height = input_height;
	int output_width = input_width;
	output.resetSize(output_width, output_height);
	int i,j,k;
	int rc = 0;
	for(i = 0; i < output_height;i++)
	{
		
		for(j = 0; j< output_width;j++)
		{
 			output.GetPixel()[j + output_width*i].r = input->GetPixel()[j + input_width*i].r/5 + input->GetPixel()[j + input_width*i].g/2 + input->GetPixel()[j + input_width*i].b/4;
 			output.GetPixel()[j + output_width*i].g = input->GetPixel()[j + input_width*i].r/5 + input->GetPixel()[j + input_width*i].g/2 + input->GetPixel()[j + input_width*i].b/4;
 			output.GetPixel()[j + output_width*i].b = input->GetPixel()[j + input_width*i].r/5 + input->GetPixel()[j + input_width*i].g/2 + input->GetPixel()[j + input_width*i].b/4;
		}		
	
	}
}
Color::Color(int width, int height, int red, int green, int blue)
{
	Image *output = GetOutput();
	output->SetFilter(this);
	output->resetSize(width, height);
	int i,j,k;
	int rc = 0;
	for(i = 0; i < height;i++)
	{
		for(j = 0; j< width;j++)
		{
 			output->GetPixel()[j + width*i].r = red;
 			output->GetPixel()[j + width*i].g = green;
 			output->GetPixel()[j + width*i].b = blue;
		}		
	
	}
}	

void Color::Execute()
{
	
}
void Color::Update()
{
	char msg[128];
	sprintf(msg, "%s: about to update input1", SourceName());
	Logger::LogEvent(msg);
	//GetInput()->Update();
	sprintf(msg, "%s: done updating input1", SourceName());
	Logger::LogEvent(msg);
}
const char *Color::SourceName()
{
	const char *Name = "Color";
	return Name;
}

const char *CheckSum::SinkName()
{
	const char *Name = "Color";
	return Name;
}
void CheckSum::OutputCheckSum(const char *filename)
{
	const Image *output = GetInput();
	int output_height = output->GetHeight();
	int output_width = output->GetWidth();
	int i,j,k;
	int Sum_R,Sum_G,Sum_B;
	Sum_R = 0;
	Sum_G = 0;
	Sum_B = 0;
	int rc = 0;
	for(i = 0; i < output_height;i++)
	{
		
		for(j = 0; j< output_width;j++)
		{
			Sum_R += output->GetPixel()[j + output_width*i].r;
			Sum_G += output->GetPixel()[j + output_width*i].g;
			Sum_B += output->GetPixel()[j + output_width*i].b;
		}
	}
	Sum_R = Sum_R % 256;
	Sum_G = Sum_G % 256;
	Sum_B = Sum_B % 256;
	FILE *CheckSumFile;
	CheckSumFile = fopen(filename, "w");
	fprintf(CheckSumFile, "CHECKSUM: %d, %d, %d\n", Sum_R, Sum_G, Sum_B);
	fclose(CheckSumFile);		
}

