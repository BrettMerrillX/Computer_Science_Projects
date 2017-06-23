#ifndef SINK
#define SINK
#include <image.h>


class sink
{
	public:
		virtual const char *SinkName() = 0;
		sink();
		void SetInput(Image *);
		void SetInput2(Image *);
		const Image *GetInput() const;
		const Image *GetInput2() const;
		bool InputExists;
		bool Input2Exists;
	protected:
		const Image *Input;
		const Image *Input2;

};

#endif
