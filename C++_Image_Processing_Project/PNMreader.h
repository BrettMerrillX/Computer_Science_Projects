#ifndef PNMREADER
#define PNMREADER
#include <source.h>

class PNMreader: public source
{
	public:
		PNMreader(char *filename);
		~PNMreader();
		const char *SourceName();
		virtual void Execute();
		void readImage(char *,Image &);
		char *filename;
		void Update();
};


#endif