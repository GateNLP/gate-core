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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,60.0,60.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -116.65451049804688f, -204.4719696044922f));
// _0_0 is CompositeGraphicsNode
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
float alpha__0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1 = g.getClip();
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -116.65451049804688f, -204.4719696044922f));
// _0_1 is CompositeGraphicsNode
float alpha__0_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_0 = g.getClip();
AffineTransform defaultTransform__0_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_0 is ShapeNode
origAlpha = alpha__0_1_0;
g.setTransform(defaultTransform__0_1_0);
g.setClip(clip__0_1_0);
float alpha__0_1_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_1 = g.getClip();
AffineTransform defaultTransform__0_1_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_1 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.63737, 234.47195);
((GeneralPath)shape).curveTo(170.80554, 263.7357, 122.67123, 258.0005, 122.67123, 258.0005);
((GeneralPath)shape).lineTo(122.67123, 210.94342);
((GeneralPath)shape).curveTo(122.67123, 210.94342, 170.46924, 205.20825, 170.63737, 234.47195);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 128, 0, 255);
stroke = new BasicStroke(2.0333996f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(170.63737, 234.47195);
((GeneralPath)shape).curveTo(170.80554, 263.7357, 122.67123, 258.0005, 122.67123, 258.0005);
((GeneralPath)shape).lineTo(122.67123, 210.94342);
((GeneralPath)shape).curveTo(122.67123, 210.94342, 170.46924, 205.20825, 170.63737, 234.47195);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_1_1;
g.setTransform(defaultTransform__0_1_1);
g.setClip(clip__0_1_1);
float alpha__0_1_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_2 = g.getClip();
AffineTransform defaultTransform__0_1_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2 is CompositeGraphicsNode
float alpha__0_1_2_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_2_0 = g.getClip();
AffineTransform defaultTransform__0_1_2_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_2_0 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(159.5505, 249.17284);
((GeneralPath)shape).lineTo(140.4545, 249.17284);
((GeneralPath)shape).curveTo(136.08382, 249.17284, 132.46115, 247.72084, 129.5865, 244.81683);
((GeneralPath)shape).curveTo(126.74117, 241.91283, 125.318504, 238.24617, 125.318504, 233.81683);
((GeneralPath)shape).curveTo(125.318504, 229.41685, 126.7265, 225.86752, 129.54251, 223.16884);
((GeneralPath)shape).curveTo(132.38785, 220.4702, 136.02518, 219.12086, 140.45451, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 224.35684);
((GeneralPath)shape).lineTo(140.45451, 224.35684);
((GeneralPath)shape).curveTo(137.57983, 224.35686, 135.20383, 225.28087, 133.3265, 227.12885);
((GeneralPath)shape).curveTo(131.4785, 228.97687, 130.5545, 231.35286, 130.5545, 234.25685);
((GeneralPath)shape).curveTo(130.5545, 237.13153, 131.4785, 239.46352, 133.3265, 241.25285);
((GeneralPath)shape).curveTo(135.20383, 243.04219, 137.57983, 243.93686, 140.45451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 249.17285);
g.setPaint(paint);
g.fill(shape);
paint = new Color(128, 0, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(159.5505, 249.17284);
((GeneralPath)shape).lineTo(140.4545, 249.17284);
((GeneralPath)shape).curveTo(136.08382, 249.17284, 132.46115, 247.72084, 129.5865, 244.81683);
((GeneralPath)shape).curveTo(126.74117, 241.91283, 125.318504, 238.24617, 125.318504, 233.81683);
((GeneralPath)shape).curveTo(125.318504, 229.41685, 126.7265, 225.86752, 129.54251, 223.16884);
((GeneralPath)shape).curveTo(132.38785, 220.4702, 136.02518, 219.12086, 140.45451, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 219.12083);
((GeneralPath)shape).lineTo(157.4385, 224.35684);
((GeneralPath)shape).lineTo(140.45451, 224.35684);
((GeneralPath)shape).curveTo(137.57983, 224.35686, 135.20383, 225.28087, 133.3265, 227.12885);
((GeneralPath)shape).curveTo(131.4785, 228.97687, 130.5545, 231.35286, 130.5545, 234.25685);
((GeneralPath)shape).curveTo(130.5545, 237.13153, 131.4785, 239.46352, 133.3265, 241.25285);
((GeneralPath)shape).curveTo(135.20383, 243.04219, 137.57983, 243.93686, 140.45451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 243.93686);
((GeneralPath)shape).lineTo(154.31451, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 237.38086);
((GeneralPath)shape).lineTo(140.05852, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 232.58485);
((GeneralPath)shape).lineTo(159.55052, 249.17285);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_1_2_0;
g.setTransform(defaultTransform__0_1_2_0);
g.setClip(clip__0_1_2_0);
origAlpha = alpha__0_1_2;
g.setTransform(defaultTransform__0_1_2);
g.setClip(clip__0_1_2);
float alpha__0_1_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_3 = g.getClip();
AffineTransform defaultTransform__0_1_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_3 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new Rectangle2D.Double(151.00753784179688, 250.3676300048828, 25.646968841552734, 14.104340553283691);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_3;
g.setTransform(defaultTransform__0_1_3);
g.setClip(clip__0_1_3);
float alpha__0_1_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_4 = g.getClip();
AffineTransform defaultTransform__0_1_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_4 is TextNode of '8.2'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(156.65524, 258.03308);
((GeneralPath)shape).quadTo(155.8115, 258.03308, 155.35837, 258.49402);
((GeneralPath)shape).quadTo(154.90524, 258.95496, 154.90524, 259.81433);
((GeneralPath)shape).quadTo(154.90524, 260.6737, 155.35837, 261.13074);
((GeneralPath)shape).quadTo(155.8115, 261.58777, 156.65524, 261.58777);
((GeneralPath)shape).quadTo(157.49118, 261.58777, 157.9365, 261.13074);
((GeneralPath)shape).quadTo(158.3818, 260.6737, 158.3818, 259.81433);
((GeneralPath)shape).quadTo(158.3818, 258.94714, 157.9365, 258.4901);
((GeneralPath)shape).quadTo(157.49118, 258.03308, 156.65524, 258.03308);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(154.45993, 257.0409);
((GeneralPath)shape).quadTo(153.39743, 256.72058, 152.85837, 256.05652);
((GeneralPath)shape).quadTo(152.3193, 255.39246, 152.3193, 254.40027);
((GeneralPath)shape).quadTo(152.3193, 252.9237, 153.42087, 252.15027);
((GeneralPath)shape).quadTo(154.52243, 251.37683, 156.65524, 251.37683);
((GeneralPath)shape).quadTo(158.77243, 251.37683, 159.874, 252.14636);
((GeneralPath)shape).quadTo(160.97556, 252.9159, 160.97556, 254.40027);
((GeneralPath)shape).quadTo(160.97556, 255.39246, 160.43259, 256.05652);
((GeneralPath)shape).quadTo(159.88962, 256.72058, 158.82712, 257.0409);
((GeneralPath)shape).quadTo(160.01462, 257.36902, 160.62009, 258.1073);
((GeneralPath)shape).quadTo(161.22556, 258.84558, 161.22556, 259.97058);
((GeneralPath)shape).quadTo(161.22556, 261.70496, 160.07321, 262.59167);
((GeneralPath)shape).quadTo(158.92087, 263.4784, 156.65524, 263.4784);
((GeneralPath)shape).quadTo(154.3818, 263.4784, 153.22165, 262.59167);
((GeneralPath)shape).quadTo(152.0615, 261.70496, 152.0615, 259.97058);
((GeneralPath)shape).quadTo(152.0615, 258.84558, 152.66696, 258.1073);
((GeneralPath)shape).quadTo(153.27243, 257.36902, 154.45993, 257.0409);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(155.16306, 254.70496);
((GeneralPath)shape).quadTo(155.16306, 255.40027, 155.54977, 255.77527);
((GeneralPath)shape).quadTo(155.9365, 256.15027, 156.65524, 256.15027);
((GeneralPath)shape).quadTo(157.35837, 256.15027, 157.74118, 255.77527);
((GeneralPath)shape).quadTo(158.124, 255.40027, 158.124, 254.70496);
((GeneralPath)shape).quadTo(158.124, 254.00964, 157.74118, 253.63855);
((GeneralPath)shape).quadTo(157.35837, 253.26746, 156.65524, 253.26746);
((GeneralPath)shape).quadTo(155.9365, 253.26746, 155.54977, 253.64246);
((GeneralPath)shape).quadTo(155.16306, 254.01746, 155.16306, 254.70496);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(162.35056, 260.2284);
((GeneralPath)shape).lineTo(165.16306, 260.2284);
((GeneralPath)shape).lineTo(165.16306, 263.25183);
((GeneralPath)shape).lineTo(162.35056, 263.25183);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(169.90524, 261.0409);
((GeneralPath)shape).lineTo(175.03806, 261.0409);
((GeneralPath)shape).lineTo(175.03806, 263.25183);
((GeneralPath)shape).lineTo(166.5615, 263.25183);
((GeneralPath)shape).lineTo(166.5615, 261.0409);
((GeneralPath)shape).lineTo(170.8193, 257.28308);
((GeneralPath)shape).quadTo(171.38962, 256.76746, 171.66306, 256.27527);
((GeneralPath)shape).quadTo(171.9365, 255.78308, 171.9365, 255.25183);
((GeneralPath)shape).quadTo(171.9365, 254.43152, 171.38571, 253.93152);
((GeneralPath)shape).quadTo(170.83493, 253.43152, 169.92087, 253.43152);
((GeneralPath)shape).quadTo(169.21774, 253.43152, 168.3818, 253.7323);
((GeneralPath)shape).quadTo(167.54587, 254.03308, 166.59274, 254.62683);
((GeneralPath)shape).lineTo(166.59274, 252.06433);
((GeneralPath)shape).quadTo(167.60837, 251.7284, 168.60056, 251.55261);
((GeneralPath)shape).quadTo(169.59274, 251.37683, 170.54587, 251.37683);
((GeneralPath)shape).quadTo(172.63962, 251.37683, 173.79977, 252.2987);
((GeneralPath)shape).quadTo(174.95993, 253.22058, 174.95993, 254.86902);
((GeneralPath)shape).quadTo(174.95993, 255.82214, 174.46774, 256.64636);
((GeneralPath)shape).quadTo(173.97556, 257.47058, 172.39743, 258.8534);
((GeneralPath)shape).closePath();
paint = new Color(0, 128, 0, 255);
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 128, 0, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
g.setStroke(stroke);
g.setPaint(paint);
g.draw(shape);
origAlpha = alpha__0_1_4;
g.setTransform(defaultTransform__0_1_4);
g.setClip(clip__0_1_4);
float alpha__0_1_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5 = g.getClip();
AffineTransform defaultTransform__0_1_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_5 is CompositeGraphicsNode
float alpha__0_1_5_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5_0 = g.getClip();
AffineTransform defaultTransform__0_1_5_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_5_0 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new Rectangle2D.Double(116.65451049804688, 255.174072265625, 35.33734893798828, 9.297898292541504);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_5_0;
g.setTransform(defaultTransform__0_1_5_0);
g.setClip(clip__0_1_5_0);
float alpha__0_1_5_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1_5_1 = g.getClip();
AffineTransform defaultTransform__0_1_5_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1_5_1 is TextNode of 'SNAPSHOT'
shape = new GeneralPath();
((GeneralPath)shape).moveTo(121.114685, 259.43054);
((GeneralPath)shape).lineTo(121.114685, 260.35632);
((GeneralPath)shape).quadTo(120.75433, 260.1952, 120.41156, 260.11316);
((GeneralPath)shape).quadTo(120.06879, 260.03113, 119.7641, 260.03113);
((GeneralPath)shape).quadTo(119.3598, 260.03113, 119.16644, 260.14246);
((GeneralPath)shape).quadTo(118.97308, 260.25378, 118.97308, 260.48816);
((GeneralPath)shape).quadTo(118.97308, 260.66394, 119.103455, 260.7621);
((GeneralPath)shape).quadTo(119.233826, 260.86023, 119.5766, 260.93054);
((GeneralPath)shape).lineTo(120.05707, 261.02722);
((GeneralPath)shape).quadTo(120.78656, 261.1737, 121.09418, 261.47253);
((GeneralPath)shape).quadTo(121.401794, 261.77136, 121.401794, 262.32214);
((GeneralPath)shape).quadTo(121.401794, 263.04578, 120.972595, 263.3988);
((GeneralPath)shape).quadTo(120.543396, 263.75183, 119.66156, 263.75183);
((GeneralPath)shape).quadTo(119.245544, 263.75183, 118.8266, 263.67273);
((GeneralPath)shape).quadTo(118.40765, 263.59363, 117.98871, 263.43835);
((GeneralPath)shape).lineTo(117.98871, 262.4862);
((GeneralPath)shape).quadTo(118.40765, 262.70886, 118.79877, 262.82166);
((GeneralPath)shape).quadTo(119.18988, 262.93445, 119.55316, 262.93445);
((GeneralPath)shape).quadTo(119.9223, 262.93445, 120.11859, 262.8114);
((GeneralPath)shape).quadTo(120.31488, 262.68835, 120.31488, 262.45984);
((GeneralPath)shape).quadTo(120.31488, 262.25476, 120.18158, 262.14343);
((GeneralPath)shape).quadTo(120.04828, 262.0321, 119.64984, 261.9442);
((GeneralPath)shape).lineTo(119.21332, 261.84753);
((GeneralPath)shape).quadTo(118.55707, 261.7069, 118.253845, 261.3993);
((GeneralPath)shape).quadTo(117.95062, 261.09167, 117.95062, 260.5702);
((GeneralPath)shape).quadTo(117.95062, 259.91687, 118.3725, 259.5653);
((GeneralPath)shape).quadTo(118.79437, 259.21375, 119.58539, 259.21375);
((GeneralPath)shape).quadTo(119.94574, 259.21375, 120.3266, 259.26794);
((GeneralPath)shape).quadTo(120.70746, 259.32214, 121.114685, 259.43054);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(121.89203, 259.29285);
((GeneralPath)shape).lineTo(123.151794, 259.29285);
((GeneralPath)shape).lineTo(124.742615, 262.29285);
((GeneralPath)shape).lineTo(124.742615, 259.29285);
((GeneralPath)shape).lineTo(125.81195, 259.29285);
((GeneralPath)shape).lineTo(125.81195, 263.66687);
((GeneralPath)shape).lineTo(124.552185, 263.66687);
((GeneralPath)shape).lineTo(122.961365, 260.66687);
((GeneralPath)shape).lineTo(122.961365, 263.66687);
((GeneralPath)shape).lineTo(121.89203, 263.66687);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(129.06781, 262.87);
((GeneralPath)shape).lineTo(127.30414, 262.87);
((GeneralPath)shape).lineTo(127.02582, 263.66687);
((GeneralPath)shape).lineTo(125.89203, 263.66687);
((GeneralPath)shape).lineTo(127.512146, 259.29285);
((GeneralPath)shape).lineTo(128.85687, 259.29285);
((GeneralPath)shape).lineTo(130.47699, 263.66687);
((GeneralPath)shape).lineTo(129.3432, 263.66687);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(127.58539, 262.05847);
((GeneralPath)shape).lineTo(128.78363, 262.05847);
((GeneralPath)shape).lineTo(128.18597, 260.31824);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(130.55707, 259.29285);
((GeneralPath)shape).lineTo(132.42914, 259.29285);
((GeneralPath)shape).quadTo(133.2641, 259.29285, 133.71088, 259.66345);
((GeneralPath)shape).quadTo(134.15765, 260.03406, 134.15765, 260.7196);
((GeneralPath)shape).quadTo(134.15765, 261.40808, 133.71088, 261.7787);
((GeneralPath)shape).quadTo(133.2641, 262.1493, 132.42914, 262.1493);
((GeneralPath)shape).lineTo(131.685, 262.1493);
((GeneralPath)shape).lineTo(131.685, 263.66687);
((GeneralPath)shape).lineTo(130.55707, 263.66687);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(131.685, 260.11023);
((GeneralPath)shape).lineTo(131.685, 261.3319);
((GeneralPath)shape).lineTo(132.30902, 261.3319);
((GeneralPath)shape).quadTo(132.63715, 261.3319, 132.81586, 261.17224);
((GeneralPath)shape).quadTo(132.99457, 261.01257, 132.99457, 260.7196);
((GeneralPath)shape).quadTo(132.99457, 260.42664, 132.81586, 260.26843);
((GeneralPath)shape).quadTo(132.63715, 260.11023, 132.30902, 260.11023);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(137.49847, 259.43054);
((GeneralPath)shape).lineTo(137.49847, 260.35632);
((GeneralPath)shape).quadTo(137.13812, 260.1952, 136.79535, 260.11316);
((GeneralPath)shape).quadTo(136.45258, 260.03113, 136.14789, 260.03113);
((GeneralPath)shape).quadTo(135.74359, 260.03113, 135.55023, 260.14246);
((GeneralPath)shape).quadTo(135.35687, 260.25378, 135.35687, 260.48816);
((GeneralPath)shape).quadTo(135.35687, 260.66394, 135.48724, 260.7621);
((GeneralPath)shape).quadTo(135.61761, 260.86023, 135.96039, 260.93054);
((GeneralPath)shape).lineTo(136.44086, 261.02722);
((GeneralPath)shape).quadTo(137.17035, 261.1737, 137.47797, 261.47253);
((GeneralPath)shape).quadTo(137.78558, 261.77136, 137.78558, 262.32214);
((GeneralPath)shape).quadTo(137.78558, 263.04578, 137.35638, 263.3988);
((GeneralPath)shape).quadTo(136.92719, 263.75183, 136.04535, 263.75183);
((GeneralPath)shape).quadTo(135.62933, 263.75183, 135.21039, 263.67273);
((GeneralPath)shape).quadTo(134.79144, 263.59363, 134.3725, 263.43835);
((GeneralPath)shape).lineTo(134.3725, 262.4862);
((GeneralPath)shape).quadTo(134.79144, 262.70886, 135.18256, 262.82166);
((GeneralPath)shape).quadTo(135.57367, 262.93445, 135.93695, 262.93445);
((GeneralPath)shape).quadTo(136.30609, 262.93445, 136.50238, 262.8114);
((GeneralPath)shape).quadTo(136.69867, 262.68835, 136.69867, 262.45984);
((GeneralPath)shape).quadTo(136.69867, 262.25476, 136.56537, 262.14343);
((GeneralPath)shape).quadTo(136.43207, 262.0321, 136.03363, 261.9442);
((GeneralPath)shape).lineTo(135.5971, 261.84753);
((GeneralPath)shape).quadTo(134.94086, 261.7069, 134.63763, 261.3993);
((GeneralPath)shape).quadTo(134.33441, 261.09167, 134.33441, 260.5702);
((GeneralPath)shape).quadTo(134.33441, 259.91687, 134.75629, 259.5653);
((GeneralPath)shape).quadTo(135.17816, 259.21375, 135.96918, 259.21375);
((GeneralPath)shape).quadTo(136.32953, 259.21375, 136.71039, 259.26794);
((GeneralPath)shape).quadTo(137.09125, 259.32214, 137.49847, 259.43054);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(138.27582, 259.29285);
((GeneralPath)shape).lineTo(139.40375, 259.29285);
((GeneralPath)shape).lineTo(139.40375, 260.95984);
((GeneralPath)shape).lineTo(141.06781, 260.95984);
((GeneralPath)shape).lineTo(141.06781, 259.29285);
((GeneralPath)shape).lineTo(142.19574, 259.29285);
((GeneralPath)shape).lineTo(142.19574, 263.66687);
((GeneralPath)shape).lineTo(141.06781, 263.66687);
((GeneralPath)shape).lineTo(141.06781, 261.81238);
((GeneralPath)shape).lineTo(139.40375, 261.81238);
((GeneralPath)shape).lineTo(139.40375, 263.66687);
((GeneralPath)shape).lineTo(138.27582, 263.66687);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(144.79535, 260.03113);
((GeneralPath)shape).quadTo(144.27972, 260.03113, 143.99554, 260.412);
((GeneralPath)shape).quadTo(143.71136, 260.79285, 143.71136, 261.48425);
((GeneralPath)shape).quadTo(143.71136, 262.17273, 143.99554, 262.5536);
((GeneralPath)shape).quadTo(144.27972, 262.93445, 144.79535, 262.93445);
((GeneralPath)shape).quadTo(145.3139, 262.93445, 145.59808, 262.5536);
((GeneralPath)shape).quadTo(145.88226, 262.17273, 145.88226, 261.48425);
((GeneralPath)shape).quadTo(145.88226, 260.79285, 145.59808, 260.412);
((GeneralPath)shape).quadTo(145.3139, 260.03113, 144.79535, 260.03113);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(144.79535, 259.21375);
((GeneralPath)shape).quadTo(145.85004, 259.21375, 146.4477, 259.81726);
((GeneralPath)shape).quadTo(147.04535, 260.42078, 147.04535, 261.48425);
((GeneralPath)shape).quadTo(147.04535, 262.5448, 146.4477, 263.14832);
((GeneralPath)shape).quadTo(145.85004, 263.75183, 144.79535, 263.75183);
((GeneralPath)shape).quadTo(143.74359, 263.75183, 143.14447, 263.14832);
((GeneralPath)shape).quadTo(142.54535, 262.5448, 142.54535, 261.48425);
((GeneralPath)shape).quadTo(142.54535, 260.42078, 143.14447, 259.81726);
((GeneralPath)shape).quadTo(143.74359, 259.21375, 144.79535, 259.21375);
((GeneralPath)shape).closePath();
((GeneralPath)shape).moveTo(146.8764, 259.29285);
((GeneralPath)shape).lineTo(150.90765, 259.29285);
((GeneralPath)shape).lineTo(150.90765, 260.1454);
((GeneralPath)shape).lineTo(149.45746, 260.1454);
((GeneralPath)shape).lineTo(149.45746, 263.66687);
((GeneralPath)shape).lineTo(148.32953, 263.66687);
((GeneralPath)shape).lineTo(148.32953, 260.1454);
((GeneralPath)shape).lineTo(146.8764, 260.1454);
((GeneralPath)shape).closePath();
paint = new Color(0, 128, 0, 255);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1_5_1;
g.setTransform(defaultTransform__0_1_5_1);
g.setClip(clip__0_1_5_1);
origAlpha = alpha__0_1_5;
g.setTransform(defaultTransform__0_1_5);
g.setClip(clip__0_1_5);
origAlpha = alpha__0_1;
g.setTransform(defaultTransform__0_1);
g.setClip(clip__0_1);
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
	 * Creates a new transcoded SVG image.
	 */
	public GATEVersionIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GATEVersionIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public GATEVersionIcon(int width, int height) {
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

