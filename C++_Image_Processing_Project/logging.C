#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fstream>
#include <logging.h>

FILE *Logger::logger = NULL;

void Logger::LogEvent(const char *event)
{
	if( logger == NULL){
		logger = fopen("logger.txt", "w");
	}
	fprintf(logger, "%s", event);
	fprintf(logger,"\n");
}
void Logger::Finalize()
{
	fclose(logger);
}

DataFlowException::DataFlowException(const char *type, const char *error)
{
	sprintf(msg,  "Throwing exception: (%s): %s ",type,error);
	Logger::LogEvent(msg);
}
