package q.project.os.bitmap;

import q.project.QProjectList;

public class A extends QProjectList {

	@Override
	protected void onInit() {
		init("Bitmap", BitmapA.class);
    	init("Canvas", CanvasA.class);
    	init("ColorFilter", ColorFilterA.class);
    	init("Matrix", MatrixA.class);
	}
	
	
}