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
public class GATEVersionIcon implements
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
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.0f, -0.0f));
clip = new Area(g.getClip());
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,60.0,60.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -992.3621826171875f));
// _0_0 is CompositeGraphicsNode
float alpha__0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 11.230769157409668f, -2.153846263885498f));
// _0_0_0 is CompositeGraphicsNode
float alpha__0_0_0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_0 = g.getClip();
AffineTransform defaultTransform__0_0_0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_0 is ShapeNode
paint = getColor(0, 150, 65, 255, disabled);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.280495, 1030.9531);
((GeneralPath)shape).lineTo(43.280495, 1019.83875);
((GeneralPath)shape).lineTo(17.264095, 1019.83875);
((GeneralPath)shape).lineTo(17.264095, 1028.647);
((GeneralPath)shape).lineTo(32.438854, 1028.647);
((GeneralPath)shape).curveTo(30.622305, 1034.1707, 26.500015, 1037.3198, 20.555134, 1037.3198);
((GeneralPath)shape).curveTo(11.951724, 1037.3198, 6.7752542, 1032.007, 6.7752542, 1022.9129);
((GeneralPath)shape).curveTo(6.7752542, 1014.3835, 11.883304, 1008.9287, 19.575874, 1008.9287);
((GeneralPath)shape).curveTo(24.051405, 1008.9287, 27.404804, 1010.6768, 29.221363, 1013.9618);
((GeneralPath)shape).lineTo(42.44319, 1013.9618);
((GeneralPath)shape).curveTo(39.852623, 1003.8951, 31.322302, 997.8762, 19.575872, 997.8762);
((GeneralPath)shape).curveTo(4.8907433, 997.8762, -5.392847, 1008.2277, -5.392847, 1022.9129);
((GeneralPath)shape).curveTo(-5.392847, 1037.5981, 4.9586926, 1047.8762, 19.643822, 1047.8762);
((GeneralPath)shape).curveTo(32.580803, 1047.8762, 40.76393, 1039.4152, 43.28049, 1030.9532);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_0;
g.setTransform(defaultTransform__0_0_0_0);
g.setClip(clip__0_0_0_0);
float alpha__0_0_0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_1 = g.getClip();
AffineTransform defaultTransform__0_0_0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_1 is TextNode of '9.1'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(24.97841, 1051.3955);
((GeneralPath)shape).lineTo(24.97841, 1049.2393);
((GeneralPath)shape).quadTo(25.69716, 1049.5778, 26.35341, 1049.7444);
((GeneralPath)shape).quadTo(27.00966, 1049.9111, 27.650286, 1049.9111);
((GeneralPath)shape).quadTo(28.994036, 1049.9111, 29.744036, 1049.1663);
((GeneralPath)shape).quadTo(30.494036, 1048.4215, 30.624245, 1046.9528);
((GeneralPath)shape).quadTo(30.092995, 1047.3434, 29.491432, 1047.5387);
((GeneralPath)shape).quadTo(28.88987, 1047.734, 28.186745, 1047.734);
((GeneralPath)shape).quadTo(26.400286, 1047.734, 25.301329, 1046.6897);
((GeneralPath)shape).quadTo(24.20237, 1045.6455, 24.20237, 1043.9424);
((GeneralPath)shape).quadTo(24.20237, 1042.0621, 25.426329, 1040.9293);
((GeneralPath)shape).quadTo(26.650286, 1039.7965, 28.70237, 1039.7965);
((GeneralPath)shape).quadTo(30.98362, 1039.7965, 32.23362, 1041.3356);
((GeneralPath)shape).quadTo(33.48362, 1042.8746, 33.48362, 1045.6871);
((GeneralPath)shape).quadTo(33.48362, 1048.5778, 32.022682, 1050.2314);
((GeneralPath)shape).quadTo(30.561745, 1051.885, 28.01487, 1051.885);
((GeneralPath)shape).quadTo(27.19716, 1051.885, 26.44716, 1051.7627);
((GeneralPath)shape).quadTo(25.69716, 1051.6403, 24.97841, 1051.3955);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(28.686745, 1045.7809);
((GeneralPath)shape).quadTo(29.47841, 1045.7809, 29.876848, 1045.2678);
((GeneralPath)shape).quadTo(30.275286, 1044.7549, 30.275286, 1043.734);
((GeneralPath)shape).quadTo(30.275286, 1042.7184, 29.876848, 1042.2028);
((GeneralPath)shape).quadTo(29.47841, 1041.6871, 28.686745, 1041.6871);
((GeneralPath)shape).quadTo(27.900286, 1041.6871, 27.501848, 1042.2028);
((GeneralPath)shape).quadTo(27.10341, 1042.7184, 27.10341, 1043.734);
((GeneralPath)shape).quadTo(27.10341, 1044.7549, 27.501848, 1045.2678);
((GeneralPath)shape).quadTo(27.900286, 1045.7809, 28.686745, 1045.7809);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(36.142475, 1048.6299);
((GeneralPath)shape).lineTo(38.954975, 1048.6299);
((GeneralPath)shape).lineTo(38.954975, 1051.6559);
((GeneralPath)shape).lineTo(36.142475, 1051.6559);
((GeneralPath)shape).lineTo(36.142475, 1048.6299);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(42.460182, 1049.5778);
((GeneralPath)shape).lineTo(45.116432, 1049.5778);
((GeneralPath)shape).lineTo(45.116432, 1042.0361);
((GeneralPath)shape).lineTo(42.392475, 1042.5986);
((GeneralPath)shape).lineTo(42.392475, 1040.5518);
((GeneralPath)shape).lineTo(45.100807, 1039.9893);
((GeneralPath)shape).lineTo(47.960182, 1039.9893);
((GeneralPath)shape).lineTo(47.960182, 1049.5778);
((GeneralPath)shape).lineTo(50.616432, 1049.5778);
((GeneralPath)shape).lineTo(50.616432, 1051.6559);
((GeneralPath)shape).lineTo(42.460182, 1051.6559);
((GeneralPath)shape).lineTo(42.460182, 1049.5778);
((GeneralPath)shape).closePath();
paint = getColor(230, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_1;
g.setTransform(defaultTransform__0_0_0_1);
g.setClip(clip__0_0_0_1);
float alpha__0_0_0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0_0_2 = g.getClip();
AffineTransform defaultTransform__0_0_0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0_0_2 is TextNode of 'SNAPSHOT'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(-6.595023, 1047.8346);
((GeneralPath)shape).lineTo(-6.595023, 1048.7604);
((GeneralPath)shape).quadTo(-6.9563513, 1048.5983, -7.2991247, 1048.5162);
((GeneralPath)shape).quadTo(-7.641898, 1048.4342, -7.9465857, 1048.4342);
((GeneralPath)shape).quadTo(-8.350883, 1048.4342, -8.544242, 1048.5455);
((GeneralPath)shape).quadTo(-8.737601, 1048.6569, -8.737601, 1048.8912);
((GeneralPath)shape).quadTo(-8.737601, 1049.067, -8.606742, 1049.1656);
((GeneralPath)shape).quadTo(-8.475883, 1049.2643, -8.134086, 1049.3346);
((GeneralPath)shape).lineTo(-7.653617, 1049.4303);
((GeneralPath)shape).quadTo(-6.923148, 1049.5768, -6.615531, 1049.8756);
((GeneralPath)shape).quadTo(-6.307914, 1050.1744, -6.307914, 1050.7252);
((GeneralPath)shape).quadTo(-6.307914, 1051.4498, -6.7376013, 1051.8033);
((GeneralPath)shape).quadTo(-7.167289, 1052.1569, -8.048148, 1052.1569);
((GeneralPath)shape).quadTo(-8.464164, 1052.1569, -8.883109, 1052.0768);
((GeneralPath)shape).quadTo(-9.302054, 1051.9967, -9.721976, 1051.8424);
((GeneralPath)shape).lineTo(-9.721976, 1050.8893);
((GeneralPath)shape).quadTo(-9.302054, 1051.1119, -8.911429, 1051.2252);
((GeneralPath)shape).quadTo(-8.520804, 1051.3385, -8.157523, 1051.3385);
((GeneralPath)shape).quadTo(-7.7883825, 1051.3385, -7.5920935, 1051.2155);
((GeneralPath)shape).quadTo(-7.3958044, 1051.0924, -7.3958044, 1050.8639);
((GeneralPath)shape).quadTo(-7.3958044, 1050.6588, -7.528617, 1050.5475);
((GeneralPath)shape).quadTo(-7.6614294, 1050.4362, -8.059867, 1050.3483);
((GeneralPath)shape).lineTo(-8.497367, 1050.2506);
((GeneralPath)shape).quadTo(-9.153617, 1050.11, -9.456351, 1049.8024);
((GeneralPath)shape).quadTo(-9.759086, 1049.4948, -9.759086, 1048.9733);
((GeneralPath)shape).quadTo(-9.759086, 1048.3209, -9.337211, 1047.9694);
((GeneralPath)shape).quadTo(-8.915336, 1047.6178, -8.12432, 1047.6178);
((GeneralPath)shape).quadTo(-7.764945, 1047.6178, -7.3840857, 1047.6715);
((GeneralPath)shape).quadTo(-7.0032263, 1047.7252, -6.595023, 1047.8346);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(-5.318656, 1047.6959);
((GeneralPath)shape).lineTo(-4.0588903, 1047.6959);
((GeneralPath)shape).lineTo(-2.4670935, 1050.6959);
((GeneralPath)shape).lineTo(-2.4670935, 1047.6959);
((GeneralPath)shape).lineTo(-1.3987341, 1047.6959);
((GeneralPath)shape).lineTo(-1.3987341, 1052.0709);
((GeneralPath)shape).lineTo(-2.6584997, 1052.0709);
((GeneralPath)shape).lineTo(-4.2483435, 1049.0709);
((GeneralPath)shape).lineTo(-4.2483435, 1052.0709);
((GeneralPath)shape).lineTo(-5.318656, 1052.0709);
((GeneralPath)shape).lineTo(-5.318656, 1047.6959);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(2.3571253, 1051.274);
((GeneralPath)shape).lineTo(0.5934534, 1051.274);
((GeneralPath)shape).lineTo(0.31610966, 1052.0709);
((GeneralPath)shape).lineTo(-0.81865597, 1052.0709);
((GeneralPath)shape).lineTo(0.8024378, 1047.6959);
((GeneralPath)shape).lineTo(2.1461878, 1047.6959);
((GeneralPath)shape).lineTo(3.7672815, 1052.0709);
((GeneralPath)shape).lineTo(2.632516, 1052.0709);
((GeneralPath)shape).lineTo(2.3571253, 1051.274);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(0.8747034, 1050.4615);
((GeneralPath)shape).lineTo(2.0739222, 1050.4615);
((GeneralPath)shape).lineTo(1.4762659, 1048.7213);
((GeneralPath)shape).lineTo(0.8747034, 1050.4615);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(4.346383, 1047.6959);
((GeneralPath)shape).lineTo(6.21943, 1047.6959);
((GeneralPath)shape).quadTo(7.0534143, 1047.6959, 7.50068, 1048.067);
((GeneralPath)shape).quadTo(7.9479456, 1048.4381, 7.9479456, 1049.1237);
((GeneralPath)shape).quadTo(7.9479456, 1049.8112, 7.50068, 1050.1823);
((GeneralPath)shape).quadTo(7.0534143, 1050.5533, 6.21943, 1050.5533);
((GeneralPath)shape).lineTo(5.4752893, 1050.5533);
((GeneralPath)shape).lineTo(5.4752893, 1052.0709);
((GeneralPath)shape).lineTo(4.346383, 1052.0709);
((GeneralPath)shape).lineTo(4.346383, 1047.6959);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(5.4752893, 1048.5143);
((GeneralPath)shape).lineTo(5.4752893, 1049.735);
((GeneralPath)shape).lineTo(6.098336, 1049.735);
((GeneralPath)shape).quadTo(6.426461, 1049.735, 6.605172, 1049.5758);
((GeneralPath)shape).quadTo(6.783883, 1049.4166, 6.783883, 1049.1237);
((GeneralPath)shape).quadTo(6.783883, 1048.8307, 6.605172, 1048.6725);
((GeneralPath)shape).quadTo(6.426461, 1048.5143, 6.098336, 1048.5143);
((GeneralPath)shape).lineTo(5.4752893, 1048.5143);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(11.788766, 1047.8346);
((GeneralPath)shape).lineTo(11.788766, 1048.7604);
((GeneralPath)shape).quadTo(11.427438, 1048.5983, 11.084664, 1048.5162);
((GeneralPath)shape).quadTo(10.741891, 1048.4342, 10.437203, 1048.4342);
((GeneralPath)shape).quadTo(10.032907, 1048.4342, 9.839547, 1048.5455);
((GeneralPath)shape).quadTo(9.646188, 1048.6569, 9.646188, 1048.8912);
((GeneralPath)shape).quadTo(9.646188, 1049.067, 9.777047, 1049.1656);
((GeneralPath)shape).quadTo(9.907907, 1049.2643, 10.249703, 1049.3346);
((GeneralPath)shape).lineTo(10.730172, 1049.4303);
((GeneralPath)shape).quadTo(11.460641, 1049.5768, 11.768258, 1049.8756);
((GeneralPath)shape).quadTo(12.075875, 1050.1744, 12.075875, 1050.7252);
((GeneralPath)shape).quadTo(12.075875, 1051.4498, 11.646188, 1051.8033);
((GeneralPath)shape).quadTo(11.2165, 1052.1569, 10.335641, 1052.1569);
((GeneralPath)shape).quadTo(9.919625, 1052.1569, 9.50068, 1052.0768);
((GeneralPath)shape).quadTo(9.081735, 1051.9967, 8.661813, 1051.8424);
((GeneralPath)shape).lineTo(8.661813, 1050.8893);
((GeneralPath)shape).quadTo(9.081735, 1051.1119, 9.47236, 1051.2252);
((GeneralPath)shape).quadTo(9.862985, 1051.3385, 10.226266, 1051.3385);
((GeneralPath)shape).quadTo(10.595407, 1051.3385, 10.791696, 1051.2155);
((GeneralPath)shape).quadTo(10.987985, 1051.0924, 10.987985, 1050.8639);
((GeneralPath)shape).quadTo(10.987985, 1050.6588, 10.855172, 1050.5475);
((GeneralPath)shape).quadTo(10.72236, 1050.4362, 10.323922, 1050.3483);
((GeneralPath)shape).lineTo(9.886422, 1050.2506);
((GeneralPath)shape).quadTo(9.230172, 1050.11, 8.927438, 1049.8024);
((GeneralPath)shape).quadTo(8.624703, 1049.4948, 8.624703, 1048.9733);
((GeneralPath)shape).quadTo(8.624703, 1048.3209, 9.046578, 1047.9694);
((GeneralPath)shape).quadTo(9.468453, 1047.6178, 10.259469, 1047.6178);
((GeneralPath)shape).quadTo(10.618844, 1047.6178, 10.999703, 1047.6715);
((GeneralPath)shape).quadTo(11.380563, 1047.7252, 11.788766, 1047.8346);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(13.065133, 1047.6959);
((GeneralPath)shape).lineTo(14.194039, 1047.6959);
((GeneralPath)shape).lineTo(14.194039, 1049.3639);
((GeneralPath)shape).lineTo(15.858102, 1049.3639);
((GeneralPath)shape).lineTo(15.858102, 1047.6959);
((GeneralPath)shape).lineTo(16.985054, 1047.6959);
((GeneralPath)shape).lineTo(16.985054, 1052.0709);
((GeneralPath)shape).lineTo(15.858102, 1052.0709);
((GeneralPath)shape).lineTo(15.858102, 1050.2155);
((GeneralPath)shape).lineTo(14.194039, 1050.2155);
((GeneralPath)shape).lineTo(14.194039, 1052.0709);
((GeneralPath)shape).lineTo(13.065133, 1052.0709);
((GeneralPath)shape).lineTo(13.065133, 1047.6959);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(20.084663, 1048.4342);
((GeneralPath)shape).quadTo(19.569038, 1048.4342, 19.284859, 1048.8151);
((GeneralPath)shape).quadTo(19.000679, 1049.1959, 19.000679, 1049.8873);
((GeneralPath)shape).quadTo(19.000679, 1050.5768, 19.284859, 1050.9576);
((GeneralPath)shape).quadTo(19.569038, 1051.3385, 20.084663, 1051.3385);
((GeneralPath)shape).quadTo(20.604195, 1051.3385, 20.888374, 1050.9576);
((GeneralPath)shape).quadTo(21.172554, 1050.5768, 21.172554, 1049.8873);
((GeneralPath)shape).quadTo(21.172554, 1049.1959, 20.888374, 1048.8151);
((GeneralPath)shape).quadTo(20.604195, 1048.4342, 20.084663, 1048.4342);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(20.084663, 1047.6178);
((GeneralPath)shape).quadTo(21.13935, 1047.6178, 21.737007, 1048.2213);
((GeneralPath)shape).quadTo(22.334663, 1048.8248, 22.334663, 1049.8873);
((GeneralPath)shape).quadTo(22.334663, 1050.9479, 21.737007, 1051.5524);
((GeneralPath)shape).quadTo(21.13935, 1052.1569, 20.084663, 1052.1569);
((GeneralPath)shape).quadTo(19.033882, 1052.1569, 18.434273, 1051.5524);
((GeneralPath)shape).quadTo(17.834663, 1050.9479, 17.834663, 1049.8873);
((GeneralPath)shape).quadTo(17.834663, 1048.8248, 18.434273, 1048.2213);
((GeneralPath)shape).quadTo(19.033882, 1047.6178, 20.084663, 1047.6178);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(22.665718, 1047.6959);
((GeneralPath)shape).lineTo(26.696968, 1047.6959);
((GeneralPath)shape).lineTo(26.696968, 1048.5494);
((GeneralPath)shape).lineTo(25.24775, 1048.5494);
((GeneralPath)shape).lineTo(25.24775, 1052.0709);
((GeneralPath)shape).lineTo(24.118843, 1052.0709);
((GeneralPath)shape).lineTo(24.118843, 1048.5494);
((GeneralPath)shape).lineTo(22.665718, 1048.5494);
((GeneralPath)shape).lineTo(22.665718, 1047.6959);
((GeneralPath)shape).closePath();
paint = getColor(230, 0, 0, 255, disabled);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0_0_2;
g.setTransform(defaultTransform__0_0_0_2);
g.setClip(clip__0_0_0_2);
origAlpha = alpha__0_0_0;
g.setTransform(defaultTransform__0_0_0);
g.setClip(clip__0_0_0);
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
        return 2;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 4;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static int getOrigWidth() {
		return 60;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 60;
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
	public GATEVersionIcon() {
        this(getOrigWidth(),getOrigHeight(),false);
	}
	
	public GATEVersionIcon(boolean disabled) {
        this(getOrigWidth(),getOrigHeight(),disabled);
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GATEVersionIcon(Dimension size) {
		this(size.width, size.height, false);
	}
	
	public GATEVersionIcon(Dimension size, boolean disabled) {
		this(size.width, size.height, disabled);
	}

	public GATEVersionIcon(int width, int height) {
		this(width, height, false);
	}
	
	public GATEVersionIcon(int width, int height, boolean disabled) {
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

