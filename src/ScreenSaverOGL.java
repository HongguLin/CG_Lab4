import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class ScreenSaverOGL implements GLEventListener {

	/**
	 * ScreenSaverOGL - this is a simple screen saver that uses JOGL 
	 * Eric McCreath 2009,2011,2017
	 * 
	 * You need to include the jogl jar files (gluegen2-rt.jar and jogl2.jar). In
	 * eclipse use "add external jars" in Project->Properties->Libaries
	 * otherwise make certain they are in the class path.  In the current linux 
         * computers there files are in the /usr/share/java directory.
	 * 
         * If you are executing from the command line then something like:
         *   javac -cp .:/usr/share/java/jogl2.jar:/usr/share/java/gluegen2-rt.jar ScreenSaverOGL.java
         *   java -cp .:/usr/share/java/jogl2.jar:/usr/share/java/gluegen2-rt.jar ScreenSaverOGL
         * should work.
         *
	 * You may also need set up the LD_LIBRARY_PATH environment variable. It should point to a
	 * directory that contains the required libraries such as: libgluegen2-rt.so, libjogl_cg.so, libjogl_awt.so,
	 * and libjogl.so. In eclipse this can be done in the "Run Configurations.."
	 * by adding an environment variable.   If you run from the command line then you may need to first run:

            LD_LIBRARY_PATH=/usr/lib/jni
            export LD_LIBRARY_PATH

	 * 
	 */

	JFrame jf;
	GLJPanel gljpanel;
	Dimension dim = new Dimension(800, 600);
	FPSAnimator animator;

	float xpos;
	float xvel;
	Texture gltexture;

	public ScreenSaverOGL() {
		jf = new JFrame();
		gljpanel = new GLJPanel();
		gljpanel.addGLEventListener(this);
         	gljpanel.requestFocusInWindow();
		jf.getContentPane().add(gljpanel);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setPreferredSize(dim);
		jf.pack();
		animator = new FPSAnimator(gljpanel, 20);
		xpos = 100.0f;
		xvel = 1.0f;
		animator.start();
	}

	public static void main(String[] args) {
		new ScreenSaverOGL();
	}

	public void display(GLAutoDrawable dr) {
		GL2 gl = (GL2) dr.getGL();
		GLU glu = new GLU();
		GLUT glut = new GLUT();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glRasterPos2f(xpos, 300.0f);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Save the Screens");
		gl.glFlush();


		//Line
		gl.glPushMatrix();
		gl.glColor3f(0f,1f,0f);
		gl.glBegin(gl.GL_LINES);
		gl.glVertex2d(300,300);
		gl.glVertex2d(500,500);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glFlush();


		//Polygon
		gl.glPushMatrix();
		gl.glColor3f(0f,0f,1f);
		gl.glBegin(gl.GL_POLYGON);
		gl.glVertex2d(66,66);
		gl.glVertex2d(233,66);
		gl.glVertex2d(233,233);
		gl.glVertex2d(66,233);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glFlush();

		gltexture.bind(gl);

		//Image
		gl.glPushMatrix();
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glColor4f(1,1,1,1);
		//gl.glTranslated(xpos,xpos,0.0f);
		gl.glTranslated(150,200,0.0f);
		gl.glRotated(3.0*(xpos-100),0,0,1);
		gl.glTranslated(-150,-200,0.0f);

		gl.glBegin(gl.GL_POLYGON);
		gl.glTexCoord2d(0,0);
		gl.glVertex2d(100,100);
		gl.glTexCoord2d(1,0);
		gl.glVertex2d(200,100);
		gl.glTexCoord2d(1,1);
		gl.glVertex2d(200,300);
		gl.glTexCoord2d(0,1);
		gl.glVertex2d(100,300);
		gl.glEnd();
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glPopMatrix();

		gl.glFlush();




		
		xpos += xvel;
		if (xpos > dim.getWidth())
			xpos = 0.0f;

	}

	public void displayChanged(GLAutoDrawable dr, boolean arg1, boolean arg2) {
	}

	public void init(GLAutoDrawable dr) {
		GL2 gl = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		glu.gluOrtho2D(0.0, dim.getWidth(), 0.0, dim.getHeight());
		gl.glMatrixMode(GL2.GL_MODELVIEW);

		try {
			gltexture = TextureIO.newTexture(new File("climb1.png"),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reshape(GLAutoDrawable dr, int arg1, int arg2, int arg3,
			int arg4) {
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	
	}
}
