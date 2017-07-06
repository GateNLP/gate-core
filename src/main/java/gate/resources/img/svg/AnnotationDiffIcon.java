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
public class AnnotationDiffIcon implements
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
g.transform(new AffineTransform(1.419052004814148f, 0.2158149927854538f, -0.39573198556900024f, 0.6445109844207764f, 0.0f, 0.0f));
// _0_0_0 is TextNode of 'annota'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(16.75259, 51.05394);
((GeneralPath)shape).quadTo(16.210888, 51.05394, 16.001669, 51.178013);
((GeneralPath)shape).quadTo(15.792448, 51.302086, 15.792448, 51.602127);
((GeneralPath)shape).quadTo(15.792448, 51.84054, 15.948957, 51.980022);
((GeneralPath)shape).quadTo(16.105467, 52.119503, 16.376318, 52.119503);
((GeneralPath)shape).quadTo(16.747725, 52.119503, 16.973162, 51.85595);
((GeneralPath)shape).quadTo(17.1986, 51.592396, 17.1986, 51.154495);
((GeneralPath)shape).lineTo(17.1986, 51.05394);
((GeneralPath)shape).lineTo(16.75259, 51.05394);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(17.646235, 50.86905);
((GeneralPath)shape).lineTo(17.646235, 52.42441);
((GeneralPath)shape).lineTo(17.1986, 52.42441);
((GeneralPath)shape).lineTo(17.1986, 52.010838);
((GeneralPath)shape).quadTo(17.044525, 52.258984, 16.815842, 52.377377);
((GeneralPath)shape).quadTo(16.58716, 52.495773, 16.2563, 52.495773);
((GeneralPath)shape).quadTo(15.83786, 52.495773, 15.591337, 52.260605);
((GeneralPath)shape).quadTo(15.344814, 52.025433, 15.344814, 51.63132);
((GeneralPath)shape).quadTo(15.344814, 51.170715, 15.652157, 50.937164);
((GeneralPath)shape).quadTo(15.959499, 50.703617, 16.570942, 50.703617);
((GeneralPath)shape).lineTo(17.1986, 50.703617);
((GeneralPath)shape).lineTo(17.1986, 50.65983);
((GeneralPath)shape).quadTo(17.1986, 50.351673, 16.995058, 50.18219);
((GeneralPath)shape).quadTo(16.791513, 50.012707, 16.424974, 50.012707);
((GeneralPath)shape).quadTo(16.191425, 50.012707, 15.970041, 50.06866);
((GeneralPath)shape).quadTo(15.748658, 50.124615, 15.544303, 50.236523);
((GeneralPath)shape).lineTo(15.544303, 49.82295);
((GeneralPath)shape).quadTo(15.789205, 49.728878, 16.020319, 49.681034);
((GeneralPath)shape).quadTo(16.251434, 49.63319, 16.470387, 49.63319);
((GeneralPath)shape).quadTo(17.062365, 49.63319, 17.3543, 49.93972);
((GeneralPath)shape).quadTo(17.646235, 50.246254, 17.646235, 50.86905);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(20.83238, 50.779846);
((GeneralPath)shape).lineTo(20.83238, 52.42441);
((GeneralPath)shape).lineTo(20.384747, 52.42441);
((GeneralPath)shape).lineTo(20.384747, 50.79444);
((GeneralPath)shape).quadTo(20.384747, 50.40682, 20.233913, 50.214626);
((GeneralPath)shape).quadTo(20.08308, 50.022434, 19.781414, 50.022434);
((GeneralPath)shape).quadTo(19.419739, 50.022434, 19.21052, 50.25355);
((GeneralPath)shape).quadTo(19.001299, 50.484665, 19.001299, 50.883644);
((GeneralPath)shape).lineTo(19.001299, 52.42441);
((GeneralPath)shape).lineTo(18.550423, 52.42441);
((GeneralPath)shape).lineTo(18.550423, 49.699684);
((GeneralPath)shape).lineTo(19.001299, 49.699684);
((GeneralPath)shape).lineTo(19.001299, 50.12299);
((GeneralPath)shape).quadTo(19.161863, 49.87647, 19.379192, 49.75483);
((GeneralPath)shape).quadTo(19.596523, 49.63319, 19.88197, 49.63319);
((GeneralPath)shape).quadTo(20.350687, 49.63319, 20.591534, 49.924313);
((GeneralPath)shape).quadTo(20.83238, 50.21544, 20.83238, 50.779846);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(23.990143, 50.779846);
((GeneralPath)shape).lineTo(23.990143, 52.42441);
((GeneralPath)shape).lineTo(23.54251, 52.42441);
((GeneralPath)shape).lineTo(23.54251, 50.79444);
((GeneralPath)shape).quadTo(23.54251, 50.40682, 23.391676, 50.214626);
((GeneralPath)shape).quadTo(23.240843, 50.022434, 22.939177, 50.022434);
((GeneralPath)shape).quadTo(22.577501, 50.022434, 22.368282, 50.25355);
((GeneralPath)shape).quadTo(22.159061, 50.484665, 22.159061, 50.883644);
((GeneralPath)shape).lineTo(22.159061, 52.42441);
((GeneralPath)shape).lineTo(21.708185, 52.42441);
((GeneralPath)shape).lineTo(21.708185, 49.699684);
((GeneralPath)shape).lineTo(22.159061, 49.699684);
((GeneralPath)shape).lineTo(22.159061, 50.12299);
((GeneralPath)shape).quadTo(22.319626, 49.87647, 22.536955, 49.75483);
((GeneralPath)shape).quadTo(22.754286, 49.63319, 23.039732, 49.63319);
((GeneralPath)shape).quadTo(23.50845, 49.63319, 23.749296, 49.924313);
((GeneralPath)shape).quadTo(23.990143, 50.21544, 23.990143, 50.779846);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(25.93962, 50.012707);
((GeneralPath)shape).quadTo(25.579567, 50.012707, 25.370346, 50.294098);
((GeneralPath)shape).quadTo(25.161125, 50.57549, 25.161125, 51.06367);
((GeneralPath)shape).quadTo(25.161125, 51.553474, 25.368725, 51.834053);
((GeneralPath)shape).quadTo(25.576323, 52.114635, 25.93962, 52.114635);
((GeneralPath)shape).quadTo(26.296429, 52.114635, 26.505648, 51.83243);
((GeneralPath)shape).quadTo(26.714869, 51.55023, 26.714869, 51.06367);
((GeneralPath)shape).quadTo(26.714869, 50.580357, 26.505648, 50.29653);
((GeneralPath)shape).quadTo(26.296429, 50.012707, 25.93962, 50.012707);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(25.93962, 49.63319);
((GeneralPath)shape).quadTo(26.523489, 49.63319, 26.856781, 50.012707);
((GeneralPath)shape).quadTo(27.190073, 50.39222, 27.190073, 51.06367);
((GeneralPath)shape).quadTo(27.190073, 51.733498, 26.856781, 52.114635);
((GeneralPath)shape).quadTo(26.523489, 52.495773, 25.93962, 52.495773);
((GeneralPath)shape).quadTo(25.352505, 52.495773, 25.020836, 52.114635);
((GeneralPath)shape).quadTo(24.689165, 51.733498, 24.689165, 51.06367);
((GeneralPath)shape).quadTo(24.689165, 50.39222, 25.020836, 50.012707);
((GeneralPath)shape).quadTo(25.352505, 49.63319, 25.93962, 49.63319);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(28.374842, 48.92606);
((GeneralPath)shape).lineTo(28.374842, 49.699684);
((GeneralPath)shape).lineTo(29.296059, 49.699684);
((GeneralPath)shape).lineTo(29.296059, 50.046764);
((GeneralPath)shape).lineTo(28.374842, 50.046764);
((GeneralPath)shape).lineTo(28.374842, 51.5259);
((GeneralPath)shape).quadTo(28.374842, 51.860004, 28.465666, 51.954884);
((GeneralPath)shape).quadTo(28.55649, 52.049763, 28.837072, 52.049763);
((GeneralPath)shape).lineTo(29.296059, 52.049763);
((GeneralPath)shape).lineTo(29.296059, 52.42441);
((GeneralPath)shape).lineTo(28.837072, 52.42441);
((GeneralPath)shape).quadTo(28.318077, 52.42441, 28.121021, 52.2306);
((GeneralPath)shape).quadTo(27.923965, 52.03679, 27.923965, 51.5259);
((GeneralPath)shape).lineTo(27.923965, 50.046764);
((GeneralPath)shape).lineTo(27.59635, 50.046764);
((GeneralPath)shape).lineTo(27.59635, 49.699684);
((GeneralPath)shape).lineTo(27.923965, 49.699684);
((GeneralPath)shape).lineTo(27.923965, 48.92606);
((GeneralPath)shape).lineTo(28.374842, 48.92606);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(31.123087, 51.05394);
((GeneralPath)shape).quadTo(30.581387, 51.05394, 30.372166, 51.178013);
((GeneralPath)shape).quadTo(30.162945, 51.302086, 30.162945, 51.602127);
((GeneralPath)shape).quadTo(30.162945, 51.84054, 30.319456, 51.980022);
((GeneralPath)shape).quadTo(30.475965, 52.119503, 30.746815, 52.119503);
((GeneralPath)shape).quadTo(31.118221, 52.119503, 31.34366, 51.85595);
((GeneralPath)shape).quadTo(31.5691, 51.592396, 31.5691, 51.154495);
((GeneralPath)shape).lineTo(31.5691, 51.05394);
((GeneralPath)shape).lineTo(31.123087, 51.05394);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(32.01673, 50.86905);
((GeneralPath)shape).lineTo(32.01673, 52.42441);
((GeneralPath)shape).lineTo(31.5691, 52.42441);
((GeneralPath)shape).lineTo(31.5691, 52.010838);
((GeneralPath)shape).quadTo(31.415022, 52.258984, 31.18634, 52.377377);
((GeneralPath)shape).quadTo(30.957657, 52.495773, 30.626799, 52.495773);
((GeneralPath)shape).quadTo(30.208357, 52.495773, 29.961836, 52.260605);
((GeneralPath)shape).quadTo(29.715313, 52.025433, 29.715313, 51.63132);
((GeneralPath)shape).quadTo(29.715313, 51.170715, 30.022655, 50.937164);
((GeneralPath)shape).quadTo(30.329998, 50.703617, 30.941439, 50.703617);
((GeneralPath)shape).lineTo(31.5691, 50.703617);
((GeneralPath)shape).lineTo(31.5691, 50.65983);
((GeneralPath)shape).quadTo(31.5691, 50.351673, 31.365555, 50.18219);
((GeneralPath)shape).quadTo(31.162012, 50.012707, 30.795471, 50.012707);
((GeneralPath)shape).quadTo(30.561924, 50.012707, 30.34054, 50.06866);
((GeneralPath)shape).quadTo(30.119156, 50.124615, 29.9148, 50.236523);
((GeneralPath)shape).lineTo(29.9148, 49.82295);
((GeneralPath)shape).quadTo(30.159702, 49.728878, 30.390818, 49.681034);
((GeneralPath)shape).quadTo(30.621933, 49.63319, 30.840883, 49.63319);
((GeneralPath)shape).quadTo(31.432863, 49.63319, 31.724798, 49.93972);
((GeneralPath)shape).quadTo(32.01673, 50.246254, 32.01673, 50.86905);
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
g.transform(new AffineTransform(0.9846190214157104f, 0.17471599578857422f, -0.34722900390625f, 0.9377800226211548f, 0.0f, 0.0f));
// _0_0_1 is ShapeNode
paint = getColor(0, 155, 0, 159, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.677866, 32.75804);
((GeneralPath)shape).lineTo(39.93438, 32.737816);
((GeneralPath)shape).lineTo(37.31607, 36.47715);
((GeneralPath)shape).lineTo(28.677866, 36.591206);
((GeneralPath)shape).lineTo(28.677866, 32.75804);
((GeneralPath)shape).closePath();
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
g.transform(new AffineTransform(0.5062599778175354f, 0.3880690038204193f, -0.3905639946460724f, 0.5030270218849182f, 18.44576072692871f, -14.269539833068848f));
// _0_0_2 is CompositeGraphicsNode
float alpha__0_0_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_0 = g.getClip();
AffineTransform defaultTransform__0_0_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_2_0 is ShapeNode
paint = getColor(0, 155, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.50621, 6.5062113);
((GeneralPath)shape).lineTo(34.50621, 57.50621);
((GeneralPath)shape).lineTo(34.59996, 57.50621);
((GeneralPath)shape).curveTo(34.590523, 57.581318, 34.50621, 57.64886, 34.50621, 57.72496);
((GeneralPath)shape).curveTo(34.50621, 59.678455, 37.72298, 61.233765, 41.97496, 61.69371);
((GeneralPath)shape).lineTo(41.97496, 71.81871);
((GeneralPath)shape).lineTo(47.59996, 67.81871);
((GeneralPath)shape).lineTo(47.34996, 61.63121);
((GeneralPath)shape).curveTo(51.258373, 61.07848, 54.13121, 59.574383, 54.13121, 57.72496);
((GeneralPath)shape).curveTo(54.13121, 57.625732, 54.022198, 57.54122, 54.00621, 57.44371);
((GeneralPath)shape).lineTo(54.00621, 6.5062113);
((GeneralPath)shape).lineTo(34.50621, 6.5062113);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0124228f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.50621, 6.5062113);
((GeneralPath)shape).lineTo(34.50621, 57.50621);
((GeneralPath)shape).lineTo(34.59996, 57.50621);
((GeneralPath)shape).curveTo(34.590523, 57.581318, 34.50621, 57.64886, 34.50621, 57.72496);
((GeneralPath)shape).curveTo(34.50621, 59.678455, 37.72298, 61.233765, 41.97496, 61.69371);
((GeneralPath)shape).lineTo(41.97496, 71.81871);
((GeneralPath)shape).lineTo(47.59996, 67.81871);
((GeneralPath)shape).lineTo(47.34996, 61.63121);
((GeneralPath)shape).curveTo(51.258373, 61.07848, 54.13121, 59.574383, 54.13121, 57.72496);
((GeneralPath)shape).curveTo(54.13121, 57.625732, 54.022198, 57.54122, 54.00621, 57.44371);
((GeneralPath)shape).lineTo(54.00621, 6.5062113);
((GeneralPath)shape).lineTo(34.50621, 6.5062113);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_0;
g.setTransform(defaultTransform__0_0_2_0);
g.setClip(clip__0_0_2_0);
float alpha__0_0_2_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_2_1 = g.getClip();
AffineTransform defaultTransform__0_0_2_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -2.318181037902832f, -7.681818008422852f));
// _0_0_2_1 is ShapeNode
paint = getColor(0, 155, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(56.545452, 14.0);
((GeneralPath)shape).curveTo(56.545452, 16.309555, 52.149704, 18.181818, 46.727272, 18.181818);
((GeneralPath)shape).curveTo(41.304836, 18.181818, 36.90909, 16.309555, 36.90909, 14.0);
((GeneralPath)shape).curveTo(36.90909, 11.690446, 41.304836, 9.818182, 46.727272, 9.818182);
((GeneralPath)shape).curveTo(52.149704, 9.818182, 56.545452, 11.690446, 56.545452, 14.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(56.545452, 14.0);
((GeneralPath)shape).curveTo(56.545452, 16.309555, 52.149704, 18.181818, 46.727272, 18.181818);
((GeneralPath)shape).curveTo(41.304836, 18.181818, 36.90909, 16.309555, 36.90909, 14.0);
((GeneralPath)shape).curveTo(36.90909, 11.690446, 41.304836, 9.818182, 46.727272, 9.818182);
((GeneralPath)shape).curveTo(52.149704, 9.818182, 56.545452, 11.690446, 56.545452, 14.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_2_1;
g.setTransform(defaultTransform__0_0_2_1);
g.setClip(clip__0_0_2_1);
origAlpha = alpha__0_0_2;
g.setTransform(defaultTransform__0_0_2);
g.setClip(clip__0_0_2);
float alpha__0_0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_3 = g.getClip();
AffineTransform defaultTransform__0_0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_3 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.69500107f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(23.570007, 33.13052);
((GeneralPath)shape).lineTo(23.570007, 33.13052);
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_4 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.55640537f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(15.05541, 32.71108);
((GeneralPath)shape).curveTo(18.431425, 35.4394, 18.431425, 34.83917, 18.431425, 34.83917);
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
g.transform(new AffineTransform(1.419052004814148f, 0.2158149927854538f, -0.39573198556900024f, 0.6445109844207764f, 0.0f, 0.0f));
// _0_0_5 is TextNode of 'annotation'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.72646, 74.66581);
((GeneralPath)shape).quadTo(36.184757, 74.66581, 35.975536, 74.78989);
((GeneralPath)shape).quadTo(35.766315, 74.913956, 35.766315, 75.214005);
((GeneralPath)shape).quadTo(35.766315, 75.452415, 35.922825, 75.591896);
((GeneralPath)shape).quadTo(36.079334, 75.73138, 36.350185, 75.73138);
((GeneralPath)shape).quadTo(36.721592, 75.73138, 36.94703, 75.46783);
((GeneralPath)shape).quadTo(37.17247, 75.20427, 37.17247, 74.766365);
((GeneralPath)shape).lineTo(37.17247, 74.66581);
((GeneralPath)shape).lineTo(36.72646, 74.66581);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(37.620102, 74.48092);
((GeneralPath)shape).lineTo(37.620102, 76.036285);
((GeneralPath)shape).lineTo(37.17247, 76.036285);
((GeneralPath)shape).lineTo(37.17247, 75.62271);
((GeneralPath)shape).quadTo(37.018394, 75.87086, 36.78971, 75.98925);
((GeneralPath)shape).quadTo(36.561028, 76.10765, 36.230167, 76.10765);
((GeneralPath)shape).quadTo(35.81173, 76.10765, 35.565205, 75.872475);
((GeneralPath)shape).quadTo(35.318684, 75.63731, 35.318684, 75.243195);
((GeneralPath)shape).quadTo(35.318684, 74.782585, 35.626026, 74.54904);
((GeneralPath)shape).quadTo(35.93337, 74.31549, 36.54481, 74.31549);
((GeneralPath)shape).lineTo(37.17247, 74.31549);
((GeneralPath)shape).lineTo(37.17247, 74.2717);
((GeneralPath)shape).quadTo(37.17247, 73.96355, 36.968925, 73.79406);
((GeneralPath)shape).quadTo(36.76538, 73.62458, 36.39884, 73.62458);
((GeneralPath)shape).quadTo(36.165295, 73.62458, 35.94391, 73.680534);
((GeneralPath)shape).quadTo(35.722527, 73.73649, 35.518173, 73.8484);
((GeneralPath)shape).lineTo(35.518173, 73.43482);
((GeneralPath)shape).quadTo(35.763073, 73.34075, 35.994186, 73.29291);
((GeneralPath)shape).quadTo(36.225304, 73.24506, 36.444256, 73.24506);
((GeneralPath)shape).quadTo(37.036232, 73.24506, 37.328167, 73.5516);
((GeneralPath)shape).quadTo(37.620102, 73.85812, 37.620102, 74.48092);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(40.80625, 74.391716);
((GeneralPath)shape).lineTo(40.80625, 76.036285);
((GeneralPath)shape).lineTo(40.358616, 76.036285);
((GeneralPath)shape).lineTo(40.358616, 74.40632);
((GeneralPath)shape).quadTo(40.358616, 74.01869, 40.207783, 73.8265);
((GeneralPath)shape).quadTo(40.05695, 73.63431, 39.755283, 73.63431);
((GeneralPath)shape).quadTo(39.39361, 73.63431, 39.184387, 73.865425);
((GeneralPath)shape).quadTo(38.97517, 74.09654, 38.97517, 74.49552);
((GeneralPath)shape).lineTo(38.97517, 76.036285);
((GeneralPath)shape).lineTo(38.524292, 76.036285);
((GeneralPath)shape).lineTo(38.524292, 73.31156);
((GeneralPath)shape).lineTo(38.97517, 73.31156);
((GeneralPath)shape).lineTo(38.97517, 73.73486);
((GeneralPath)shape).quadTo(39.135735, 73.48834, 39.35306, 73.3667);
((GeneralPath)shape).quadTo(39.570393, 73.24506, 39.85584, 73.24506);
((GeneralPath)shape).quadTo(40.32456, 73.24506, 40.565403, 73.53619);
((GeneralPath)shape).quadTo(40.80625, 73.82731, 40.80625, 74.391716);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(43.964012, 74.391716);
((GeneralPath)shape).lineTo(43.964012, 76.036285);
((GeneralPath)shape).lineTo(43.516376, 76.036285);
((GeneralPath)shape).lineTo(43.516376, 74.40632);
((GeneralPath)shape).quadTo(43.516376, 74.01869, 43.365543, 73.8265);
((GeneralPath)shape).quadTo(43.21471, 73.63431, 42.913044, 73.63431);
((GeneralPath)shape).quadTo(42.55137, 73.63431, 42.342148, 73.865425);
((GeneralPath)shape).quadTo(42.13293, 74.09654, 42.13293, 74.49552);
((GeneralPath)shape).lineTo(42.13293, 76.036285);
((GeneralPath)shape).lineTo(41.682053, 76.036285);
((GeneralPath)shape).lineTo(41.682053, 73.31156);
((GeneralPath)shape).lineTo(42.13293, 73.31156);
((GeneralPath)shape).lineTo(42.13293, 73.73486);
((GeneralPath)shape).quadTo(42.293495, 73.48834, 42.510822, 73.3667);
((GeneralPath)shape).quadTo(42.728153, 73.24506, 43.0136, 73.24506);
((GeneralPath)shape).quadTo(43.48232, 73.24506, 43.723164, 73.53619);
((GeneralPath)shape).quadTo(43.964012, 73.82731, 43.964012, 74.391716);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(45.91349, 73.62458);
((GeneralPath)shape).quadTo(45.553436, 73.62458, 45.344215, 73.90597);
((GeneralPath)shape).quadTo(45.134995, 74.18736, 45.134995, 74.675545);
((GeneralPath)shape).quadTo(45.134995, 75.165344, 45.342594, 75.44593);
((GeneralPath)shape).quadTo(45.55019, 75.72651, 45.91349, 75.72651);
((GeneralPath)shape).quadTo(46.270298, 75.72651, 46.47952, 75.444305);
((GeneralPath)shape).quadTo(46.68874, 75.1621, 46.68874, 74.675545);
((GeneralPath)shape).quadTo(46.68874, 74.19223, 46.47952, 73.9084);
((GeneralPath)shape).quadTo(46.270298, 73.62458, 45.91349, 73.62458);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(45.91349, 73.24506);
((GeneralPath)shape).quadTo(46.49736, 73.24506, 46.83065, 73.62458);
((GeneralPath)shape).quadTo(47.163944, 74.0041, 47.163944, 74.675545);
((GeneralPath)shape).quadTo(47.163944, 75.345375, 46.83065, 75.72651);
((GeneralPath)shape).quadTo(46.49736, 76.10765, 45.91349, 76.10765);
((GeneralPath)shape).quadTo(45.326374, 76.10765, 44.994705, 75.72651);
((GeneralPath)shape).quadTo(44.663033, 75.345375, 44.663033, 74.675545);
((GeneralPath)shape).quadTo(44.663033, 74.0041, 44.994705, 73.62458);
((GeneralPath)shape).quadTo(45.326374, 73.24506, 45.91349, 73.24506);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(48.348713, 72.53793);
((GeneralPath)shape).lineTo(48.348713, 73.31156);
((GeneralPath)shape).lineTo(49.269928, 73.31156);
((GeneralPath)shape).lineTo(49.269928, 73.65864);
((GeneralPath)shape).lineTo(48.348713, 73.65864);
((GeneralPath)shape).lineTo(48.348713, 75.13777);
((GeneralPath)shape).quadTo(48.348713, 75.47188, 48.439537, 75.56676);
((GeneralPath)shape).quadTo(48.53036, 75.66164, 48.810944, 75.66164);
((GeneralPath)shape).lineTo(49.269928, 75.66164);
((GeneralPath)shape).lineTo(49.269928, 76.036285);
((GeneralPath)shape).lineTo(48.810944, 76.036285);
((GeneralPath)shape).quadTo(48.291946, 76.036285, 48.09489, 75.842476);
((GeneralPath)shape).quadTo(47.897835, 75.64866, 47.897835, 75.13777);
((GeneralPath)shape).lineTo(47.897835, 73.65864);
((GeneralPath)shape).lineTo(47.570217, 73.65864);
((GeneralPath)shape).lineTo(47.570217, 73.31156);
((GeneralPath)shape).lineTo(47.897835, 73.31156);
((GeneralPath)shape).lineTo(47.897835, 72.53793);
((GeneralPath)shape).lineTo(48.348713, 72.53793);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(51.09696, 74.66581);
((GeneralPath)shape).quadTo(50.555256, 74.66581, 50.346035, 74.78989);
((GeneralPath)shape).quadTo(50.136814, 74.913956, 50.136814, 75.214005);
((GeneralPath)shape).quadTo(50.136814, 75.452415, 50.293324, 75.591896);
((GeneralPath)shape).quadTo(50.449833, 75.73138, 50.720684, 75.73138);
((GeneralPath)shape).quadTo(51.09209, 75.73138, 51.317528, 75.46783);
((GeneralPath)shape).quadTo(51.54297, 75.20427, 51.54297, 74.766365);
((GeneralPath)shape).lineTo(51.54297, 74.66581);
((GeneralPath)shape).lineTo(51.09696, 74.66581);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(51.9906, 74.48092);
((GeneralPath)shape).lineTo(51.9906, 76.036285);
((GeneralPath)shape).lineTo(51.54297, 76.036285);
((GeneralPath)shape).lineTo(51.54297, 75.62271);
((GeneralPath)shape).quadTo(51.388893, 75.87086, 51.16021, 75.98925);
((GeneralPath)shape).quadTo(50.931526, 76.10765, 50.600666, 76.10765);
((GeneralPath)shape).quadTo(50.182228, 76.10765, 49.935703, 75.872475);
((GeneralPath)shape).quadTo(49.689182, 75.63731, 49.689182, 75.243195);
((GeneralPath)shape).quadTo(49.689182, 74.782585, 49.996525, 74.54904);
((GeneralPath)shape).quadTo(50.303867, 74.31549, 50.91531, 74.31549);
((GeneralPath)shape).lineTo(51.54297, 74.31549);
((GeneralPath)shape).lineTo(51.54297, 74.2717);
((GeneralPath)shape).quadTo(51.54297, 73.96355, 51.339424, 73.79406);
((GeneralPath)shape).quadTo(51.13588, 73.62458, 50.76934, 73.62458);
((GeneralPath)shape).quadTo(50.535793, 73.62458, 50.314407, 73.680534);
((GeneralPath)shape).quadTo(50.093025, 73.73649, 49.88867, 73.8484);
((GeneralPath)shape).lineTo(49.88867, 73.43482);
((GeneralPath)shape).quadTo(50.13357, 73.34075, 50.364685, 73.29291);
((GeneralPath)shape).quadTo(50.595802, 73.24506, 50.814754, 73.24506);
((GeneralPath)shape).quadTo(51.40673, 73.24506, 51.698666, 73.5516);
((GeneralPath)shape).quadTo(51.9906, 73.85812, 51.9906, 74.48092);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(53.355396, 72.53793);
((GeneralPath)shape).lineTo(53.355396, 73.31156);
((GeneralPath)shape).lineTo(54.27661, 73.31156);
((GeneralPath)shape).lineTo(54.27661, 73.65864);
((GeneralPath)shape).lineTo(53.355396, 73.65864);
((GeneralPath)shape).lineTo(53.355396, 75.13777);
((GeneralPath)shape).quadTo(53.355396, 75.47188, 53.44622, 75.56676);
((GeneralPath)shape).quadTo(53.537045, 75.66164, 53.817627, 75.66164);
((GeneralPath)shape).lineTo(54.27661, 75.66164);
((GeneralPath)shape).lineTo(54.27661, 76.036285);
((GeneralPath)shape).lineTo(53.817627, 76.036285);
((GeneralPath)shape).quadTo(53.29863, 76.036285, 53.101574, 75.842476);
((GeneralPath)shape).quadTo(52.90452, 75.64866, 52.90452, 75.13777);
((GeneralPath)shape).lineTo(52.90452, 73.65864);
((GeneralPath)shape).lineTo(52.5769, 73.65864);
((GeneralPath)shape).lineTo(52.5769, 73.31156);
((GeneralPath)shape).lineTo(52.90452, 73.31156);
((GeneralPath)shape).lineTo(52.90452, 72.53793);
((GeneralPath)shape).lineTo(53.355396, 72.53793);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(54.86616, 73.31156);
((GeneralPath)shape).lineTo(55.313793, 73.31156);
((GeneralPath)shape).lineTo(55.313793, 76.036285);
((GeneralPath)shape).lineTo(54.86616, 76.036285);
((GeneralPath)shape).lineTo(54.86616, 73.31156);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(54.86616, 72.25086);
((GeneralPath)shape).lineTo(55.313793, 72.25086);
((GeneralPath)shape).lineTo(55.313793, 72.816895);
((GeneralPath)shape).lineTo(54.86616, 72.816895);
((GeneralPath)shape).lineTo(54.86616, 72.25086);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(57.306248, 73.62458);
((GeneralPath)shape).quadTo(56.946194, 73.62458, 56.736973, 73.90597);
((GeneralPath)shape).quadTo(56.527752, 74.18736, 56.527752, 74.675545);
((GeneralPath)shape).quadTo(56.527752, 75.165344, 56.73535, 75.44593);
((GeneralPath)shape).quadTo(56.942947, 75.72651, 57.306248, 75.72651);
((GeneralPath)shape).quadTo(57.663055, 75.72651, 57.872276, 75.444305);
((GeneralPath)shape).quadTo(58.081497, 75.1621, 58.081497, 74.675545);
((GeneralPath)shape).quadTo(58.081497, 74.19223, 57.872276, 73.9084);
((GeneralPath)shape).quadTo(57.663055, 73.62458, 57.306248, 73.62458);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(57.306248, 73.24506);
((GeneralPath)shape).quadTo(57.890118, 73.24506, 58.223408, 73.62458);
((GeneralPath)shape).quadTo(58.5567, 74.0041, 58.5567, 74.675545);
((GeneralPath)shape).quadTo(58.5567, 75.345375, 58.223408, 75.72651);
((GeneralPath)shape).quadTo(57.890118, 76.10765, 57.306248, 76.10765);
((GeneralPath)shape).quadTo(56.71913, 76.10765, 56.387463, 75.72651);
((GeneralPath)shape).quadTo(56.05579, 75.345375, 56.05579, 74.675545);
((GeneralPath)shape).quadTo(56.05579, 74.0041, 56.387463, 73.62458);
((GeneralPath)shape).quadTo(56.71913, 73.24506, 57.306248, 73.24506);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(61.56282, 74.391716);
((GeneralPath)shape).lineTo(61.56282, 76.036285);
((GeneralPath)shape).lineTo(61.115185, 76.036285);
((GeneralPath)shape).lineTo(61.115185, 74.40632);
((GeneralPath)shape).quadTo(61.115185, 74.01869, 60.96435, 73.8265);
((GeneralPath)shape).quadTo(60.81352, 73.63431, 60.511852, 73.63431);
((GeneralPath)shape).quadTo(60.150177, 73.63431, 59.940956, 73.865425);
((GeneralPath)shape).quadTo(59.73174, 74.09654, 59.73174, 74.49552);
((GeneralPath)shape).lineTo(59.73174, 76.036285);
((GeneralPath)shape).lineTo(59.28086, 76.036285);
((GeneralPath)shape).lineTo(59.28086, 73.31156);
((GeneralPath)shape).lineTo(59.73174, 73.31156);
((GeneralPath)shape).lineTo(59.73174, 73.73486);
((GeneralPath)shape).quadTo(59.892303, 73.48834, 60.10963, 73.3667);
((GeneralPath)shape).quadTo(60.32696, 73.24506, 60.612408, 73.24506);
((GeneralPath)shape).quadTo(61.081127, 73.24506, 61.32197, 73.53619);
((GeneralPath)shape).quadTo(61.56282, 73.82731, 61.56282, 74.391716);
((GeneralPath)shape).closePath();
paint = getColor(0, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_5;
g.setTransform(defaultTransform__0_0_5);
g.setClip(clip__0_0_5);
float alpha__0_0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_6 = g.getClip();
AffineTransform defaultTransform__0_0_6 = g.getTransform();
g.transform(new AffineTransform(0.9846190214157104f, 0.17471599578857422f, -0.34722900390625f, 0.9377800226211548f, 0.0f, 0.0f));
// _0_0_6 is ShapeNode
paint = getColor(255, 0, 0, 135, disabled);
shape = new Rectangle2D.Double(53.67605972290039, 48.92516326904297, 23.635759353637695, 3.8331644535064697);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_6;
g.setTransform(defaultTransform__0_0_6);
g.setClip(clip__0_0_6);
float alpha__0_0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_7 = g.getClip();
AffineTransform defaultTransform__0_0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_7 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(52.37377, 21.922834);
((GeneralPath)shape).lineTo(32.45501, 47.57721);
((GeneralPath)shape).lineTo(32.50247, 47.613594);
((GeneralPath)shape).curveTo(32.468357, 47.64771, 32.399296, 47.648968, 32.36957, 47.68725);
((GeneralPath)shape).curveTo(31.606607, 48.669907, 32.62768, 50.7006, 34.60065, 52.582027);
((GeneralPath)shape).lineTo(30.64619, 57.675175);
((GeneralPath)shape).lineTo(35.05616, 57.845955);
((GeneralPath)shape).lineTo(37.34621, 54.63646);
((GeneralPath)shape).curveTo(39.540756, 55.875153, 41.582607, 56.23341, 42.304924, 55.3031);
((GeneralPath)shape).curveTo(42.34368, 55.253185, 42.3215, 55.168373, 42.351486, 55.113117);
((GeneralPath)shape).lineTo(62.245842, 29.49018);
((GeneralPath)shape).lineTo(52.37377, 21.922834);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.64527816f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(52.37377, 21.922834);
((GeneralPath)shape).lineTo(32.45501, 47.57721);
((GeneralPath)shape).lineTo(32.50247, 47.613594);
((GeneralPath)shape).curveTo(32.468357, 47.64771, 32.399296, 47.648968, 32.36957, 47.68725);
((GeneralPath)shape).curveTo(31.606607, 48.669907, 32.62768, 50.7006, 34.60065, 52.582027);
((GeneralPath)shape).lineTo(30.64619, 57.675175);
((GeneralPath)shape).lineTo(35.05616, 57.845955);
((GeneralPath)shape).lineTo(37.34621, 54.63646);
((GeneralPath)shape).curveTo(39.540756, 55.875153, 41.582607, 56.23341, 42.304924, 55.3031);
((GeneralPath)shape).curveTo(42.34368, 55.253185, 42.3215, 55.168373, 42.351486, 55.113117);
((GeneralPath)shape).lineTo(62.245842, 29.49018);
((GeneralPath)shape).lineTo(52.37377, 21.922834);
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
g.transform(new AffineTransform(0.5062599778175354f, 0.3880690038204193f, -0.3905639946460724f, 0.5030270218849182f, 39.27238845825195f, 0.49546700716018677f));
// _0_0_8 is ShapeNode
paint = getColor(255, 0, 0, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(56.545452, 14.0);
((GeneralPath)shape).curveTo(56.545452, 16.309555, 52.149704, 18.181818, 46.727272, 18.181818);
((GeneralPath)shape).curveTo(41.304836, 18.181818, 36.90909, 16.309555, 36.90909, 14.0);
((GeneralPath)shape).curveTo(36.90909, 11.690446, 41.304836, 9.818182, 46.727272, 9.818182);
((GeneralPath)shape).curveTo(52.149704, 9.818182, 56.545452, 11.690446, 56.545452, 14.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(56.545452, 14.0);
((GeneralPath)shape).curveTo(56.545452, 16.309555, 52.149704, 18.181818, 46.727272, 18.181818);
((GeneralPath)shape).curveTo(41.304836, 18.181818, 36.90909, 16.309555, 36.90909, 14.0);
((GeneralPath)shape).curveTo(36.90909, 11.690446, 41.304836, 9.818182, 46.727272, 9.818182);
((GeneralPath)shape).curveTo(52.149704, 9.818182, 56.545452, 11.690446, 56.545452, 14.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
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
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.69500107f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.570007, 52.659306);
((GeneralPath)shape).lineTo(42.570007, 52.659306);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_9;
g.setTransform(defaultTransform__0_0_9);
g.setClip(clip__0_0_9);
float alpha__0_0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_10 = g.getClip();
AffineTransform defaultTransform__0_0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_10 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(0.55640537f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(34.05541, 52.239864);
((GeneralPath)shape).curveTo(37.431423, 54.968185, 37.431423, 54.367954, 37.431423, 54.367954);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_10;
g.setTransform(defaultTransform__0_0_10);
g.setClip(clip__0_0_10);
float alpha__0_0_11 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_11 = g.getClip();
AffineTransform defaultTransform__0_0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_11 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0077822f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(0.500032, 64.49222);
((GeneralPath)shape).curveTo(64.49997, 0.50778, 64.49997, 0.50778, 64.49997, 0.50778);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_11;
g.setTransform(defaultTransform__0_0_11);
g.setClip(clip__0_0_11);
float alpha__0_0_12 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_12 = g.getClip();
AffineTransform defaultTransform__0_0_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_12 is ShapeNode
paint = getColor(0, 0, 0, 255, disabled);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new Rectangle2D.Double(0.5, 0.5, 64.0, 64.0);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_0_12;
g.setTransform(defaultTransform__0_0_12);
g.setClip(clip__0_0_12);
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
	public AnnotationDiffIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public AnnotationDiffIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public AnnotationDiffIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public AnnotationDiffIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public AnnotationDiffIcon(int width, int height) {
		this(width, height, false);
	}
	
	public AnnotationDiffIcon(int width, int height, boolean disabled) {
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

