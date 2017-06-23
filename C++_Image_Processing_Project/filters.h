#ifndef FILTERS
#define FILTERS
#include <sink.h>
#include <source.h>
#include <logging.h>

class filters: public source, public sink
{
	public:
		virtual const char *FilterName() = 0;
		const char *SourceName() { return FilterName(); };
		const char *SinkName() { return FilterName(); };
		
		void ReadImage(char *filename, Image &output);
		void WriteImage(char *filename, Image &img);
		void HalveInSize(Image input, Image &output);
		void LeftRightConcatenate(Image &leftInput, Image &rightinput, Image &output);
		void TopBottomConcatenate(Image &topInput, Image &bottomInput, Image &output);
		void Blend(Image &input1, Image &input2, float factor, Image &output);
};

class Shrinker: public filters
{
	public:
		Shrinker();
		void Execute();
		void HalveInSize(const Image *, Image &);
		void Update();
		virtual const char *FilterName();

};

class LRConcat: public filters
{
	public:
		LRConcat();
		void Execute();
		void LeftRightConcatenate(const Image *, const Image *, Image &);
		void Update();
		virtual const char *FilterName();
};

class TBConcat: public filters
{
	public:
		TBConcat();
		void Execute();
		void TopBottomConcatenate(const Image *,const Image *, Image &);
		void Update();
		virtual const char *FilterName();

};

class Blender: public filters
{
	public: 
		Blender();
		void SetFactor(float);
		void Execute();
		void Blend(const Image *, const Image *, float, Image &);
		void Update();
		virtual const char *FilterName();
	private:
		
		float factorNum;
};

class Mirror: public filters
{
	public:
		Mirror();
		void MirrorImage(const Image *, Image &);
		void Execute();
		void Update();
		virtual const char *FilterName();

};

class Rotate: public filters
{
	public:
		Rotate();
		void RotateImage(const Image *, Image &);
		void Execute();
		void Update();
		virtual const char *FilterName();

};

class Subtract: public filters
{
	public:
		Subtract();
		void SubtractImage(const Image *, const Image *, Image &);
		void Execute();
		void Update();
		virtual const char *FilterName();

};

class Blur: public filters
{
	public:
		Blur();
		void BlurImage(const Image *, Image &);
		void Execute();
		void Update();
		virtual const char *FilterName();

};

class Grayscale: public filters
{
	public:
		Grayscale();
		void GrayscaleImage(const Image *, Image &);
		void Execute();
		void Update();
		virtual const char *FilterName();

};

class Color: public source
{
	public:
		Color(int, int, int, int, int);
		void Execute();
		void Update();
		virtual const char *SourceName();

};

class CheckSum: public sink
{
	public:	
		void OutputCheckSum(const char *);
		void Execute(){};
		void Update(){};
		virtual const char *SinkName();
};
#endif


