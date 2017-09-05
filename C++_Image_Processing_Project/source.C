#include <stdlib.h>
#include <stdio.h>
#include <source.h>

Image *source::GetOutput()
{
	return &img;
}
void source::UpdateSource(bool check)
{	
	char msg[128];
	if(!check){
		Update();
		
		Logger::LogEvent(msg);
		Execute();
	}else
	{
		sprintf(msg, "%s: about to execute", SourceName()); 
		Logger::LogEvent(msg);
		Execute();
	}
	Logger::LogEvent(msg);
	
}
