#ifndef PNMWRITER
#define PNMWRITER
#include <sink.h>

class PNMwriter: public sink
{
	public:
		void Write(char *);
		void WriteImage(char *, const Image *);
		const char *SinkName();
};


#endif