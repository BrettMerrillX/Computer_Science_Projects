#ifndef IMAGE_H
#define IMAGE_H
class filters;
class source;

struct Pixel
{
	public:
		unsigned char r;
		unsigned char g;
		unsigned char b;

};

class Image
{
	public:
		void resetSize(int w, int h);
		Image (); //default
		Image (int w, int h); //parameterized
		Image (Image &img); //COPY
		~Image(); //destructor
		void SetFilter(source *);
		void Update() const;
		int GetWidth() const;
		void SetWidth(int);
		int GetHeight() const;
		void SetHeight(int);
		Pixel *GetPixel() const;
		bool isUpdated;
		source *sourceX;
		
	private:
		int width;
		int height;
		Pixel *pixel; 
		
};

#endif