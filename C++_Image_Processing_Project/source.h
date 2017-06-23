#ifndef SOURCE
#define SOURCE
#include <image.h>
#include <logging.h>
class source
{
	public:
		virtual void Update() = 0;
		virtual const char *SourceName() = 0;
		Image *GetOutput();
		virtual void UpdateSource(bool);
		
	protected:
		Image img;
		virtual void Execute() = 0;
};
#endif
