package gate.resources.img.svg;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class has been automatically generated using <a
 * href="http://englishjavadrinker.blogspot.com/search/label/SVGRoundTrip">SVGRoundTrip</a>.
 */
@SuppressWarnings("unused")
public class TokeniserIcon implements
		javax.swing.Icon {
	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public static void paint(Graphics2D g) {
        Shape shape = null;
        Paint paint = null;
        Stroke stroke = null;
        Area clip = null;
         
        float origAlpha = 1.0f;
        Composite origComposite = g.getComposite();
        if (origComposite instanceof AlphaComposite) {
            AlphaComposite origAlphaComposite = 
                (AlphaComposite)origComposite;
            if (origAlphaComposite.getRule() == AlphaComposite.SRC_OVER) {
                origAlpha = origAlphaComposite.getAlpha();
            }
        }
        
	    Shape clip_ = g.getClip();
AffineTransform defaultTransform_ = g.getTransform();
//  is CompositeGraphicsNode
float alpha__0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0 = g.getClip();
AffineTransform defaultTransform__0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
clip = new Area(g.getClip());
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,64.0,64.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(0.5118160247802734f, -1.0447629690170288f, 0.8719090223312378f, 0.6909840106964111f, -18.355670928955078f, 27.191640853881836f));
// _0_0_0 is ShapeNode
paint = new Color(255, 97, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.545454, 33.727272);
((GeneralPath)shape).curveTo(26.545454, 43.21653, 24.673191, 50.90909, 22.363636, 50.90909);
((GeneralPath)shape).curveTo(20.054081, 50.90909, 18.181818, 43.21653, 18.181818, 33.727272);
((GeneralPath)shape).curveTo(18.181818, 24.238016, 20.054081, 16.545454, 22.363636, 16.545454);
((GeneralPath)shape).curveTo(24.673191, 16.545454, 26.545454, 24.238016, 26.545454, 33.727272);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.25f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.545454, 33.727272);
((GeneralPath)shape).curveTo(26.545454, 43.21653, 24.673191, 50.90909, 22.363636, 50.90909);
((GeneralPath)shape).curveTo(20.054081, 50.90909, 18.181818, 43.21653, 18.181818, 33.727272);
((GeneralPath)shape).curveTo(18.181818, 24.238016, 20.054081, 16.545454, 22.363636, 16.545454);
((GeneralPath)shape).curveTo(24.673191, 16.545454, 26.545454, 24.238016, 26.545454, 33.727272);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(0.9358270168304443f, -0.0985756516456604f, 0.10935000330209732f, 0.843621015548706f, -1.4215350151062012f, 11.102020263671875f));
// _0_0_1 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(3.749998f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.000004, 52.090908);
((GeneralPath)shape).curveTo(42.000004, 58.86895, 38.9474, 64.36364, 35.18182, 64.36364);
((GeneralPath)shape).curveTo(31.416243, 64.36364, 28.363638, 58.86895, 28.363638, 52.090908);
((GeneralPath)shape).curveTo(28.363638, 45.312866, 31.416243, 39.81818, 35.18182, 39.81818);
((GeneralPath)shape).curveTo(38.9474, 39.81818, 42.000004, 45.312866, 42.000004, 52.090908);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2 is TextNode of 'T'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(44.048836, 22.607834);
((GeneralPath)shape).lineTo(26.8405, 22.607834);
((GeneralPath)shape).lineTo(26.8405, 58.712);
((GeneralPath)shape).lineTo(19.278, 58.712);
((GeneralPath)shape).lineTo(19.278, 22.607834);
((GeneralPath)shape).lineTo(2.0071666, 22.607834);
((GeneralPath)shape).lineTo(2.0071666, 15.003667);
((GeneralPath)shape).lineTo(44.048836, 15.003667);
((GeneralPath)shape).lineTo(44.048836, 22.607834);
((GeneralPath)shape).closePath();
paint = new Color(0, 0, 0, 255);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
float alpha__0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0679670572280884f, -0.5443029999732971f, 0.25115299224853516f, 1.1004819869995117f, -0.2795239984989166f, -4.18129301071167f));
// _0_0_3 is ShapeNode
paint = new Color(255, 97, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.545454, 33.727272);
((GeneralPath)shape).curveTo(26.545454, 43.21653, 24.673191, 50.90909, 22.363636, 50.90909);
((GeneralPath)shape).curveTo(20.054081, 50.90909, 18.181818, 43.21653, 18.181818, 33.727272);
((GeneralPath)shape).curveTo(18.181818, 24.238016, 20.054081, 16.545454, 22.363636, 16.545454);
((GeneralPath)shape).curveTo(24.673191, 16.545454, 26.545454, 24.238016, 26.545454, 33.727272);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(1.25f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.545454, 33.727272);
((GeneralPath)shape).curveTo(26.545454, 43.21653, 24.673191, 50.90909, 22.363636, 50.90909);
((GeneralPath)shape).curveTo(20.054081, 50.90909, 18.181818, 43.21653, 18.181818, 33.727272);
((GeneralPath)shape).curveTo(18.181818, 24.238016, 20.054081, 16.545454, 22.363636, 16.545454);
((GeneralPath)shape).curveTo(24.673191, 16.545454, 26.545454, 24.238016, 26.545454, 33.727272);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
float alpha__0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(0.4622730016708374f, -0.8952839970588684f, 0.9289990067481995f, 0.44549599289894104f, -14.049229621887207f, 51.66307830810547f));
// _0_0_4 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(3.7499995f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.000004, 52.090908);
((GeneralPath)shape).curveTo(42.000004, 58.86895, 38.9474, 64.36364, 35.18182, 64.36364);
((GeneralPath)shape).curveTo(31.416243, 64.36364, 28.363638, 58.86895, 28.363638, 52.090908);
((GeneralPath)shape).curveTo(28.363638, 45.312866, 31.416243, 39.81818, 35.18182, 39.81818);
((GeneralPath)shape).curveTo(38.9474, 39.81818, 42.000004, 45.312866, 42.000004, 52.090908);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_4;
g.setTransform(defaultTransform__0_0_4);
g.setClip(clip__0_0_4);
float alpha__0_0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_5 = g.getClip();
AffineTransform defaultTransform__0_0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 5.5113630294799805f, -0.30681800842285156f));
// _0_0_5 is ShapeNode
paint = new Color(0, 0, 0, 255);
stroke = new BasicStroke(3.7499998f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(30.18182, 32.727272);
((GeneralPath)shape).curveTo(30.18182, 33.02852, 29.774805, 33.272728, 29.272728, 33.272728);
((GeneralPath)shape).curveTo(28.770653, 33.272728, 28.363638, 33.02852, 28.363638, 32.727272);
((GeneralPath)shape).curveTo(28.363638, 32.426025, 28.770653, 32.181816, 29.272728, 32.181816);
((GeneralPath)shape).curveTo(29.774805, 32.181816, 30.18182, 32.426025, 30.18182, 32.727272);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
origAlpha = alpha__0;
g.setTransform(defaultTransform__0);
g.setClip(clip__0);
g.setTransform(defaultTransform_);
g.setClip(clip_);

	}
	
	public Image getImage() {
		BufferedImage image =
            new BufferedImage(getIconWidth(), getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = image.createGraphics();
    	paintIcon(null, g, 0, 0);
    	g.dispose();
    	return image;
	}

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public static int getOrigX() {
        return 3;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 0;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static int getOrigWidth() {
		return 64;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 64;
	}

	/**
	 * The current width of this resizable icon.
	 */
	int width;

	/**
	 * The current height of this resizable icon.
	 */
	int height;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public TokeniserIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public TokeniserIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public TokeniserIcon(int width, int height) {
	this.width = width;
	this.height = height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconHeight()
	 */
    @Override
	public int getIconHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconWidth()
	 */
    @Override
	public int getIconWidth() {
		return width;
	}

	public void setDimension(Dimension newDimension) {
		this.width = newDimension.width;
		this.height = newDimension.height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics,
	 * int, int)
	 */
    @Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(x, y);
						
		Area clip = new Area(new Rectangle(0, 0, this.width, this.height));		
		if (g2d.getClip() != null) clip.intersect(new Area(g2d.getClip()));		
		g2d.setClip(clip);

		double coef1 = (double) this.width / (double) getOrigWidth();
		double coef2 = (double) this.height / (double) getOrigHeight();
		double coef = Math.min(coef1, coef2);
		g2d.scale(coef, coef);
		paint(g2d);
		g2d.dispose();
	}
}

