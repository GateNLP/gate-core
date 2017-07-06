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
public class OrthoMatcherIcon implements
		javax.swing.Icon {
		
	private static Color getColor(int red, int green, int blue, int alpha, boolean disabled) {
		
		if (!disabled) return new Color(red, green, blue, alpha);
		
		int gray = (int)(((0.30f * red) + (0.59f * green) + (0.11f * blue))/3f);
		
		gray = Math.min(255, Math.max(0, gray));
		
		//This brightens the image the same as GrayFilter
		int percent = 50;		
		gray = (255 - ((255 - gray) * (100 - percent) / 100));

		return new Color(gray, gray, gray, alpha);
	}
	
	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public static void paint(Graphics2D g, boolean disabled) {
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0 is TextNode of 'A'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.351562, 5.7207036);
((GeneralPath)shape).lineTo(12.605469, 15.87435);
((GeneralPath)shape).lineTo(20.115885, 15.87435);
((GeneralPath)shape).lineTo(16.351562, 5.7207036);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(14.792969, 2.9954433);
((GeneralPath)shape).lineTo(17.928385, 2.9954433);
((GeneralPath)shape).lineTo(25.703125, 23.41211);
((GeneralPath)shape).lineTo(22.832031, 23.41211);
((GeneralPath)shape).lineTo(20.972656, 18.171225);
((GeneralPath)shape).lineTo(11.776041, 18.171225);
((GeneralPath)shape).lineTo(9.916667, 23.41211);
((GeneralPath)shape).lineTo(7.0, 23.41211);
((GeneralPath)shape).lineTo(14.792969, 2.9954433);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
float alpha__0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_1 is TextNode of 'a'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(49.98242, 13.389086);
((GeneralPath)shape).quadTo(46.938152, 13.389086, 45.76237, 14.086351);
((GeneralPath)shape).quadTo(44.58659, 14.783617, 44.58659, 16.469814);
((GeneralPath)shape).quadTo(44.58659, 17.809658, 45.466145, 18.593512);
((GeneralPath)shape).quadTo(46.345703, 19.377367, 47.86784, 19.377367);
((GeneralPath)shape).quadTo(49.95508, 19.377367, 51.222004, 17.896246);
((GeneralPath)shape).quadTo(52.488934, 16.415127, 52.488934, 13.954189);
((GeneralPath)shape).lineTo(52.488934, 13.389086);
((GeneralPath)shape).lineTo(49.98242, 13.389086);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(55.00456, 12.350023);
((GeneralPath)shape).lineTo(55.00456, 21.090908);
((GeneralPath)shape).lineTo(52.488934, 21.090908);
((GeneralPath)shape).lineTo(52.488934, 18.76669);
((GeneralPath)shape).quadTo(51.623047, 20.16122, 50.33789, 20.826586);
((GeneralPath)shape).quadTo(49.052734, 21.49195, 47.19336, 21.49195);
((GeneralPath)shape).quadTo(44.841797, 21.49195, 43.45638, 20.170336);
((GeneralPath)shape).quadTo(42.070965, 18.84872, 42.070965, 16.633877);
((GeneralPath)shape).quadTo(42.070965, 14.045336, 43.798176, 12.732836);
((GeneralPath)shape).quadTo(45.52539, 11.420336, 48.96159, 11.420336);
((GeneralPath)shape).lineTo(52.488934, 11.420336);
((GeneralPath)shape).lineTo(52.488934, 11.174242);
((GeneralPath)shape).quadTo(52.488934, 9.442471, 51.34505, 8.489997);
((GeneralPath)shape).quadTo(50.20117, 7.5375233, 48.141277, 7.5375233);
((GeneralPath)shape).quadTo(46.828777, 7.5375233, 45.584637, 7.8519764);
((GeneralPath)shape).quadTo(44.340496, 8.1664295, 43.19206, 8.795336);
((GeneralPath)shape).lineTo(43.19206, 6.471117);
((GeneralPath)shape).quadTo(44.56836, 5.942471, 45.867188, 5.6735907);
((GeneralPath)shape).quadTo(47.166016, 5.404711, 48.396484, 5.404711);
((GeneralPath)shape).quadTo(51.72331, 5.404711, 53.363934, 7.127367);
((GeneralPath)shape).quadTo(55.00456, 8.850023, 55.00456, 12.350023);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_1;
g.setTransform(defaultTransform__0_0_1);
g.setClip(clip__0_0_1);
float alpha__0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2 is TextNode of 'A'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(48.648438, 39.720703);
((GeneralPath)shape).lineTo(44.902344, 49.874348);
((GeneralPath)shape).lineTo(52.41276, 49.874348);
((GeneralPath)shape).lineTo(48.648438, 39.720703);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(47.089844, 36.995445);
((GeneralPath)shape).lineTo(50.22526, 36.995445);
((GeneralPath)shape).lineTo(58.0, 57.41211);
((GeneralPath)shape).lineTo(55.128906, 57.41211);
((GeneralPath)shape).lineTo(53.26953, 52.171223);
((GeneralPath)shape).lineTo(44.072918, 52.171223);
((GeneralPath)shape).lineTo(42.213543, 57.41211);
((GeneralPath)shape).lineTo(39.296875, 57.41211);
((GeneralPath)shape).lineTo(47.089844, 36.995445);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3 is TextNode of 'a'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.982422, 48.90169);
((GeneralPath)shape).quadTo(13.93815, 48.90169, 12.762369, 49.598957);
((GeneralPath)shape).quadTo(11.586589, 50.296223, 11.586589, 51.98242);
((GeneralPath)shape).quadTo(11.586589, 53.322266, 12.4661455, 54.10612);
((GeneralPath)shape).quadTo(13.345703, 54.889973, 14.867838, 54.889973);
((GeneralPath)shape).quadTo(16.955078, 54.889973, 18.222004, 53.408855);
((GeneralPath)shape).quadTo(19.488932, 51.927734, 19.488932, 49.466797);
((GeneralPath)shape).lineTo(19.488932, 48.90169);
((GeneralPath)shape).lineTo(16.982422, 48.90169);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.004557, 47.86263);
((GeneralPath)shape).lineTo(22.004557, 56.603516);
((GeneralPath)shape).lineTo(19.488932, 56.603516);
((GeneralPath)shape).lineTo(19.488932, 54.279297);
((GeneralPath)shape).quadTo(18.623047, 55.67383, 17.33789, 56.33919);
((GeneralPath)shape).quadTo(16.052734, 57.00456, 14.193359, 57.00456);
((GeneralPath)shape).quadTo(11.841797, 57.00456, 10.45638, 55.68294);
((GeneralPath)shape).quadTo(9.070964, 54.36133, 9.070964, 52.146484);
((GeneralPath)shape).quadTo(9.070964, 49.55794, 10.798177, 48.24544);
((GeneralPath)shape).quadTo(12.525391, 46.93294, 15.961588, 46.93294);
((GeneralPath)shape).lineTo(19.488932, 46.93294);
((GeneralPath)shape).lineTo(19.488932, 46.686848);
((GeneralPath)shape).quadTo(19.488932, 44.95508, 18.34505, 44.002605);
((GeneralPath)shape).quadTo(17.201172, 43.05013, 15.141275, 43.05013);
((GeneralPath)shape).quadTo(13.828775, 43.05013, 12.584635, 43.364582);
((GeneralPath)shape).quadTo(11.340495, 43.679035, 10.192058, 44.30794);
((GeneralPath)shape).lineTo(10.192058, 41.983723);
((GeneralPath)shape).quadTo(11.568359, 41.45508, 12.8671875, 41.1862);
((GeneralPath)shape).quadTo(14.166016, 40.917316, 15.396484, 40.917316);
((GeneralPath)shape).quadTo(18.723307, 40.917316, 20.363932, 42.639973);
((GeneralPath)shape).quadTo(22.004557, 44.36263, 22.004557, 47.86263);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_3;
g.setTransform(defaultTransform__0_0_3);
g.setClip(clip__0_0_3);
float alpha__0_0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_4 = g.getClip();
AffineTransform defaultTransform__0_0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -1.045454978942871f, -1.0909090042114258f));
// _0_0_4 is ShapeNode
paint = getColor(255, 96, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,2,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.636366, 16.09091);
((GeneralPath)shape).curveTo(31.636366, 21.125107, 28.864017, 25.776897, 24.363638, 28.293995);
((GeneralPath)shape).curveTo(19.863258, 30.811094, 14.3185625, 30.811094, 9.818183, 28.293995);
((GeneralPath)shape).curveTo(5.317803, 25.776897, 2.545455, 21.125107, 2.545455, 16.09091);
((GeneralPath)shape).curveTo(2.545455, 11.056713, 5.317803, 6.404923, 9.818183, 3.8878243);
((GeneralPath)shape).curveTo(14.3185625, 1.3707256, 19.863258, 1.3707256, 24.363638, 3.8878243);
((GeneralPath)shape).curveTo(28.864017, 6.404923, 31.636366, 11.056713, 31.636366, 16.09091);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 31.25143051147461f, 32.730289459228516f));
// _0_0_5 is ShapeNode
paint = getColor(255, 96, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,2,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.636366, 16.09091);
((GeneralPath)shape).curveTo(31.636366, 21.125107, 28.864017, 25.776897, 24.363638, 28.293995);
((GeneralPath)shape).curveTo(19.863258, 30.811094, 14.3185625, 30.811094, 9.818183, 28.293995);
((GeneralPath)shape).curveTo(5.317803, 25.776897, 2.545455, 21.125107, 2.545455, 16.09091);
((GeneralPath)shape).curveTo(2.545455, 11.056713, 5.317803, 6.404923, 9.818183, 3.8878243);
((GeneralPath)shape).curveTo(14.3185625, 1.3707256, 19.863258, 1.3707256, 24.363638, 3.8878243);
((GeneralPath)shape).curveTo(28.864017, 6.404923, 31.636366, 11.056713, 31.636366, 16.09091);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 31.454540252685547f, -1.5000009536743164f));
// _0_0_6 is ShapeNode
paint = getColor(10, 180, 19, 255, disabled);
stroke = new BasicStroke(1.0f,0,2,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.636366, 16.09091);
((GeneralPath)shape).curveTo(31.636366, 21.125107, 28.864017, 25.776897, 24.363638, 28.293995);
((GeneralPath)shape).curveTo(19.863258, 30.811094, 14.3185625, 30.811094, 9.818183, 28.293995);
((GeneralPath)shape).curveTo(5.317803, 25.776897, 2.545455, 21.125107, 2.545455, 16.09091);
((GeneralPath)shape).curveTo(2.545455, 11.056713, 5.317803, 6.404923, 9.818183, 3.8878243);
((GeneralPath)shape).curveTo(14.3185625, 1.3707256, 19.863258, 1.3707256, 24.363638, 3.8878243);
((GeneralPath)shape).curveTo(28.864017, 6.404923, 31.636366, 11.056713, 31.636366, 16.09091);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.045454978942871f, 32.5f));
// _0_0_7 is ShapeNode
paint = getColor(10, 180, 19, 255, disabled);
stroke = new BasicStroke(1.0f,0,2,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.636366, 16.09091);
((GeneralPath)shape).curveTo(31.636366, 21.125107, 28.864017, 25.776897, 24.363638, 28.293995);
((GeneralPath)shape).curveTo(19.863258, 30.811094, 14.3185625, 30.811094, 9.818183, 28.293995);
((GeneralPath)shape).curveTo(5.317803, 25.776897, 2.545455, 21.125107, 2.545455, 16.09091);
((GeneralPath)shape).curveTo(2.545455, 11.056713, 5.317803, 6.404923, 9.818183, 3.8878243);
((GeneralPath)shape).curveTo(14.3185625, 1.3707256, 19.863258, 1.3707256, 24.363638, 3.8878243);
((GeneralPath)shape).curveTo(28.864017, 6.404923, 31.636366, 11.056713, 31.636366, 16.09091);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_7;
g.setTransform(defaultTransform__0_0_7);
g.setClip(clip__0_0_7);
float alpha__0_0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_8 = g.getClip();
AffineTransform defaultTransform__0_0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_8 is ShapeNode
paint = getColor(10, 180, 19, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.170774, 36.674606);
((GeneralPath)shape).curveTo(25.725416, 36.65529, 29.330496, 33.89018, 31.808575, 31.039085);
((GeneralPath)shape).curveTo(33.215317, 29.548405, 34.31669, 27.818468, 35.42024, 26.10058);
((GeneralPath)shape).curveTo(35.744766, 25.585007, 35.96036, 25.014757, 36.19614, 24.456112);
((GeneralPath)shape).lineTo(37.85929, 23.692251);
((GeneralPath)shape).curveTo(37.599617, 24.25799, 37.371902, 24.839346, 37.05771, 25.378485);
((GeneralPath)shape).curveTo(35.975803, 27.137878, 34.826103, 28.869637, 33.39733, 30.369059);
((GeneralPath)shape).curveTo(30.637344, 33.470997, 27.490368, 36.07503, 23.686989, 37.82735);
((GeneralPath)shape).lineTo(25.170774, 36.674606);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_8;
g.setTransform(defaultTransform__0_0_8);
g.setClip(clip__0_0_8);
float alpha__0_0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_9 = g.getClip();
AffineTransform defaultTransform__0_0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_9 is ShapeNode
paint = getColor(255, 96, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(38.476273, 36.976036);
((GeneralPath)shape).curveTo(37.9067, 36.958878, 34.20458, 34.50268, 31.659794, 31.970106);
((GeneralPath)shape).curveTo(30.215183, 30.645962, 29.084164, 29.109291, 27.950909, 27.583323);
((GeneralPath)shape).curveTo(27.617647, 27.125345, 27.396248, 26.618805, 27.154121, 26.12257);
((GeneralPath)shape).lineTo(25.446201, 25.444048);
((GeneralPath)shape).curveTo(25.712864, 25.946583, 25.94671, 26.46299, 26.269363, 26.941895);
((GeneralPath)shape).curveTo(27.380388, 28.504734, 28.561037, 30.043022, 30.028269, 31.374931);
((GeneralPath)shape).curveTo(32.862553, 34.13033, 36.09424, 36.443447, 40.0, 38.0);
((GeneralPath)shape).lineTo(38.476273, 36.976036);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_9;
g.setTransform(defaultTransform__0_0_9);
g.setClip(clip__0_0_9);
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
        return 0;
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
	 * Should this icon be drawn in a disabled state
	 */
	boolean disabled = false;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public OrthoMatcherIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public OrthoMatcherIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public OrthoMatcherIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public OrthoMatcherIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public OrthoMatcherIcon(int width, int height) {
		this(width, height, false);
	}
	
	public OrthoMatcherIcon(int width, int height, boolean disabled) {
		this.width = width;
		this.height = height;
		this.disabled = disabled;
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
		paint(g2d, disabled);
		g2d.dispose();
	}
}

