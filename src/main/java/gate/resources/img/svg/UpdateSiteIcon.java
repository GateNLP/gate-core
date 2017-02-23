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
public class UpdateSiteIcon implements
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
clip.intersect(new Area(new Rectangle2D.Double(0.0,0.0,48.0,48.0)));
g.setClip(clip);
// _0 is CompositeGraphicsNode
float alpha__0_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_0 = g.getClip();
AffineTransform defaultTransform__0_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_0 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(1.6422368288040161, 117.82710266113281), new Point2D.Double(15.343062400817871, 117.82710266113281), new float[] {0.0f,0.23762377f,0.7810999f,1.0f}, new Color[] {new Color(104, 104, 104, 0),new Color(104, 104, 104, 255),new Color(104, 104, 104, 255),new Color(104, 104, 104, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-2.7401649951934814f, 0.0f, 0.0f, 0.3649420142173767f, 47.66123962402344f, -4.017762184143066f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.66124, 36.982246);
((GeneralPath)shape).lineTo(6.118717, 36.982246);
((GeneralPath)shape).lineTo(6.118717, 40.982246);
((GeneralPath)shape).lineTo(42.66124, 40.982246);
((GeneralPath)shape).lineTo(42.66124, 36.982246);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_0;
g.setTransform(defaultTransform__0_0);
g.setClip(clip__0_0);
float alpha__0_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_1 = g.getClip();
AffineTransform defaultTransform__0_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_1 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(0.6121002435684204, 372.57818603515625), new Point2D.Double(5.08563756942749, 372.57818603515625), new float[] {0.0f,0.1f,0.9f,1.0f}, new Color[] {new Color(71, 71, 71, 0),new Color(71, 71, 71, 255),new Color(71, 71, 71, 255),new Color(71, 71, 71, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-8.168597221374512f, 0.0f, 0.0f, 0.22121000289916992f, 47.66123962402344f, -41.939170837402344f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.66124, 39.984222);
((GeneralPath)shape).lineTo(6.118717, 39.984222);
((GeneralPath)shape).lineTo(6.118717, 40.973816);
((GeneralPath)shape).lineTo(42.66124, 40.973816);
((GeneralPath)shape).lineTo(42.66124, 39.984222);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_1;
g.setTransform(defaultTransform__0_1);
g.setClip(clip__0_1);
float alpha__0_2 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_2 = g.getClip();
AffineTransform defaultTransform__0_2 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_2 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(0.6121002435684204, 372.57818603515625), new Point2D.Double(5.08563756942749, 372.57818603515625), new float[] {0.0f,0.1f,0.9f,1.0f}, new Color[] {new Color(71, 71, 71, 0),new Color(71, 71, 71, 255),new Color(71, 71, 71, 255),new Color(71, 71, 71, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-8.168597221374512f, 0.0f, 0.0f, 0.2286210060119629f, 47.66123962402344f, -40.550628662109375f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.66124, 36.017567);
((GeneralPath)shape).lineTo(6.118717, 36.017567);
((GeneralPath)shape).lineTo(6.118717, 37.040314);
((GeneralPath)shape).lineTo(42.66124, 37.040314);
((GeneralPath)shape).lineTo(42.66124, 36.017567);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_2;
g.setTransform(defaultTransform__0_2);
g.setClip(clip__0_2);
float alpha__0_3 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_3 = g.getClip();
AffineTransform defaultTransform__0_3 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_3 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(1.6422368288040161, 117.82710266113281), new Point2D.Double(15.343062400817871, 117.82710266113281), new float[] {0.0f,0.10827128f,0.920539f,1.0f}, new Color[] {new Color(255, 255, 255, 0),new Color(255, 255, 255, 180),new Color(255, 255, 255, 180),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-2.7401649951934814f, 0.0f, 0.0f, 0.18247100710868835f, 47.66123962402344f, 16.499940872192383f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(42.66124, 37.0);
((GeneralPath)shape).lineTo(6.118717, 37.0);
((GeneralPath)shape).lineTo(6.118717, 39.0);
((GeneralPath)shape).lineTo(42.66124, 39.0);
((GeneralPath)shape).lineTo(42.66124, 37.0);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_3;
g.setTransform(defaultTransform__0_3);
g.setClip(clip__0_3);
float alpha__0_4 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_4 = g.getClip();
AffineTransform defaultTransform__0_4 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_4 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(23.100046157836914, 38.29674530029297), new Point2D.Double(23.100046157836914, 43.91546630859375), new float[] {0.0f,0.1980198f,0.5990099f,1.0f}, new Color[] {new Color(122, 122, 122, 255),new Color(197, 197, 197, 255),new Color(98, 98, 98, 255),new Color(136, 136, 136, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.983801007270813f, 0.0f, 0.0f, 1.0164660215377808f, 1.1187169551849365f, -3.0177619457244873f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.584707, 26.450993);
((GeneralPath)shape).curveTo(21.801233, 26.450993, 21.178457, 27.073767, 21.178457, 27.857243);
((GeneralPath)shape).lineTo(21.178457, 35.450993);
((GeneralPath)shape).lineTo(18.115957, 35.450993);
((GeneralPath)shape).curveTo(17.332483, 35.450993, 16.709707, 36.07377, 16.709707, 36.857243);
((GeneralPath)shape).lineTo(16.709707, 40.575993);
((GeneralPath)shape).curveTo(16.709707, 41.359467, 17.332483, 41.982246, 18.115957, 41.982243);
((GeneralPath)shape).lineTo(30.303457, 41.982243);
((GeneralPath)shape).curveTo(31.086931, 41.982243, 31.709707, 41.359463, 31.709707, 40.575993);
((GeneralPath)shape).lineTo(31.709707, 36.857243);
((GeneralPath)shape).curveTo(31.709707, 36.07377, 31.086931, 35.450993, 30.303457, 35.450993);
((GeneralPath)shape).lineTo(27.709707, 35.450993);
((GeneralPath)shape).lineTo(27.709707, 27.857243);
((GeneralPath)shape).curveTo(27.709707, 27.073769, 27.086931, 26.450993, 26.303457, 26.450993);
((GeneralPath)shape).lineTo(22.584707, 26.450993);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(67, 67, 67, 255);
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(22.584707, 26.450993);
((GeneralPath)shape).curveTo(21.801233, 26.450993, 21.178457, 27.073767, 21.178457, 27.857243);
((GeneralPath)shape).lineTo(21.178457, 35.450993);
((GeneralPath)shape).lineTo(18.115957, 35.450993);
((GeneralPath)shape).curveTo(17.332483, 35.450993, 16.709707, 36.07377, 16.709707, 36.857243);
((GeneralPath)shape).lineTo(16.709707, 40.575993);
((GeneralPath)shape).curveTo(16.709707, 41.359467, 17.332483, 41.982246, 18.115957, 41.982243);
((GeneralPath)shape).lineTo(30.303457, 41.982243);
((GeneralPath)shape).curveTo(31.086931, 41.982243, 31.709707, 41.359463, 31.709707, 40.575993);
((GeneralPath)shape).lineTo(31.709707, 36.857243);
((GeneralPath)shape).curveTo(31.709707, 36.07377, 31.086931, 35.450993, 30.303457, 35.450993);
((GeneralPath)shape).lineTo(27.709707, 35.450993);
((GeneralPath)shape).lineTo(27.709707, 27.857243);
((GeneralPath)shape).curveTo(27.709707, 27.073769, 27.086931, 26.450993, 26.303457, 26.450993);
((GeneralPath)shape).lineTo(22.584707, 26.450993);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_4;
g.setTransform(defaultTransform__0_4);
g.setClip(clip__0_4);
float alpha__0_5 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_5 = g.getClip();
AffineTransform defaultTransform__0_5 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_5 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(36.37306594848633, 22.227985382080078), new Point2D.Double(38.10511779785156, 22.227985382080078), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 115),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-1.1386909484863281f, 0.0f, 0.0f, 1.7320510149002075f, 68.07896423339844f, -4.51776123046875f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.361683, 32.482246);
((GeneralPath)shape).lineTo(24.988523, 32.482246);
((GeneralPath)shape).curveTo(24.822569, 32.482246, 24.688967, 32.61585, 24.688967, 32.781803);
((GeneralPath)shape).lineTo(24.688967, 35.182693);
((GeneralPath)shape).curveTo(24.688967, 35.348648, 24.822569, 35.48225, 24.988523, 35.48225);
((GeneralPath)shape).lineTo(26.361683, 35.48225);
((GeneralPath)shape).curveTo(26.527637, 35.48225, 26.66124, 35.348648, 26.66124, 35.182693);
((GeneralPath)shape).lineTo(26.66124, 32.781803);
((GeneralPath)shape).curveTo(26.66124, 32.61585, 26.527637, 32.482246, 26.361683, 32.482246);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_5;
g.setTransform(defaultTransform__0_5);
g.setClip(clip__0_5);
float alpha__0_6 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_6 = g.getClip();
AffineTransform defaultTransform__0_6 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_6 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.247644424438477, 15.716078758239746), 29.99335f, new Point2D.Double(18.247644424438477, 15.716078758239746), new float[] {0.0f,0.15517241f,0.75f,1.0f}, new Color[] {new Color(211, 233, 255, 255),new Color(211, 233, 255, 255),new Color(64, 116, 174, 255),new Color(54, 72, 108, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.6749669909477234f, 0.0f, 0.0f, 0.6749809980392456f, 41.03192901611328f, 3.589838981628418f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.360171, 19.441938);
((GeneralPath)shape).curveTo(11.360171, 26.67072, 17.220366, 32.530846, 24.448153, 32.530846);
((GeneralPath)shape).curveTo(31.676601, 32.530846, 37.536465, 26.670652, 37.536465, 19.441936);
((GeneralPath)shape).curveTo(37.536465, 12.213489, 31.676601, 6.35396, 24.448154, 6.35396);
((GeneralPath)shape).curveTo(17.220367, 6.35396, 11.360173, 12.21349, 11.360173, 19.441936);
((GeneralPath)shape).lineTo(11.360173, 19.441936);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(57, 57, 108, 255);
stroke = new BasicStroke(2.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(11.360171, 19.441938);
((GeneralPath)shape).curveTo(11.360171, 26.67072, 17.220366, 32.530846, 24.448153, 32.530846);
((GeneralPath)shape).curveTo(31.676601, 32.530846, 37.536465, 26.670652, 37.536465, 19.441936);
((GeneralPath)shape).curveTo(37.536465, 12.213489, 31.676601, 6.35396, 24.448154, 6.35396);
((GeneralPath)shape).curveTo(17.220367, 6.35396, 11.360173, 12.21349, 11.360173, 19.441936);
((GeneralPath)shape).lineTo(11.360173, 19.441936);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_6;
g.setTransform(defaultTransform__0_6);
g.setClip(clip__0_6);
float alpha__0_7 = origAlpha;
origAlpha = origAlpha * 0.42159382f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_7 = g.getClip();
AffineTransform defaultTransform__0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_7 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(11.82690715789795, 10.476452827453613), 32.66485f, new Point2D.Double(11.82690715789795, 10.476452827453613), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.6655367016792297f, 0.0f, 0.0f, 0.5722368955612183f, 14.538100242614746f, 3.5898420810699463f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(31.865019, 13.897408);
((GeneralPath)shape).curveTo(31.866138, 17.362465, 28.599485, 20.171959, 24.569473, 20.171959);
((GeneralPath)shape).curveTo(20.539463, 20.171959, 17.272808, 17.362465, 17.27393, 13.897408);
((GeneralPath)shape).curveTo(17.272808, 10.432352, 20.539463, 7.6228585, 24.569473, 7.6228585);
((GeneralPath)shape).curveTo(28.599485, 7.6228585, 31.866138, 10.432352, 31.865019, 13.897408);
((GeneralPath)shape).lineTo(31.865019, 13.897408);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_7;
g.setTransform(defaultTransform__0_7);
g.setClip(clip__0_7);
float alpha__0_8 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_8 = g.getClip();
AffineTransform defaultTransform__0_8 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_8 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(37.16901, 17.482252);
((GeneralPath)shape).curveTo(37.16901, 17.656443, 37.16901, 17.482252, 37.16901, 17.482252);
((GeneralPath)shape).lineTo(36.8077, 17.891502);
((GeneralPath)shape).curveTo(36.586235, 17.630516, 36.33758, 17.41104, 36.085083, 17.181812);
((GeneralPath)shape).lineTo(35.53082, 17.263372);
((GeneralPath)shape).lineTo(35.02443, 16.690939);
((GeneralPath)shape).lineTo(35.02443, 17.399368);
((GeneralPath)shape).lineTo(35.45828, 17.727657);
((GeneralPath)shape).lineTo(35.747047, 18.054686);
((GeneralPath)shape).lineTo(36.132957, 17.61825);
((GeneralPath)shape).curveTo(36.2301, 17.800198, 36.325912, 17.982145, 36.42239, 18.164093);
((GeneralPath)shape).lineTo(36.42239, 18.70934);
((GeneralPath)shape).lineTo(35.987877, 19.200148);
((GeneralPath)shape).lineTo(35.192715, 19.74599);
((GeneralPath)shape).lineTo(34.59051, 20.346935);
((GeneralPath)shape).lineTo(34.2046, 19.909174);
((GeneralPath)shape).lineTo(34.397556, 19.418365);
((GeneralPath)shape).lineTo(34.012245, 18.98193);
((GeneralPath)shape).lineTo(33.361504, 17.590998);
((GeneralPath)shape).lineTo(32.80724, 16.964191);
((GeneralPath)shape).lineTo(32.662155, 17.127375);
((GeneralPath)shape).lineTo(32.87971, 17.91869);
((GeneralPath)shape).lineTo(33.288963, 18.38231);
((GeneralPath)shape).curveTo(33.522697, 19.057056, 33.75391, 19.701963, 34.06085, 20.346935);
((GeneralPath)shape).curveTo(34.5368, 20.346935, 34.985504, 20.29641, 35.45821, 20.236864);
((GeneralPath)shape).lineTo(35.45821, 20.618929);
((GeneralPath)shape).lineTo(34.879944, 22.037378);
((GeneralPath)shape).lineTo(34.349617, 22.636997);
((GeneralPath)shape).lineTo(33.915768, 23.565569);
((GeneralPath)shape).curveTo(33.915768, 24.074545, 33.915768, 24.58352, 33.915768, 25.09243);
((GeneralPath)shape).lineTo(34.06085, 25.693377);
((GeneralPath)shape).lineTo(33.81995, 25.965368);
((GeneralPath)shape).lineTo(33.288963, 26.292994);
((GeneralPath)shape).lineTo(32.734695, 26.756617);
((GeneralPath)shape).lineTo(33.193146, 27.274677);
((GeneralPath)shape).lineTo(32.56634, 27.821182);
((GeneralPath)shape).lineTo(32.686756, 28.174734);
((GeneralPath)shape).lineTo(31.746517, 29.239302);
((GeneralPath)shape).lineTo(31.120375, 29.239302);
((GeneralPath)shape).lineTo(30.590048, 29.566927);
((GeneralPath)shape).lineTo(30.252012, 29.566927);
((GeneralPath)shape).lineTo(30.252012, 29.130491);
((GeneralPath)shape).lineTo(30.108257, 28.256292);
((GeneralPath)shape).curveTo(29.921734, 27.708462, 29.727518, 27.16454, 29.52999, 26.62062);
((GeneralPath)shape).curveTo(29.52999, 26.219128, 29.553926, 25.821548, 29.57793, 25.420122);
((GeneralPath)shape).lineTo(29.819489, 24.874876);
((GeneralPath)shape).lineTo(29.481453, 24.219559);
((GeneralPath)shape).lineTo(29.506052, 23.319502);
((GeneralPath)shape).lineTo(29.047602, 22.80144);
((GeneralPath)shape).lineTo(29.276827, 22.051569);
((GeneralPath)shape).lineTo(28.903849, 21.628393);
((GeneralPath)shape).lineTo(28.252443, 21.628393);
((GeneralPath)shape).lineTo(28.035551, 21.38299);
((GeneralPath)shape).lineTo(27.38481, 21.792572);
((GeneralPath)shape).lineTo(27.119978, 21.4918);
((GeneralPath)shape).lineTo(26.517109, 22.010126);
((GeneralPath)shape).curveTo(26.107859, 21.546173, 25.697947, 21.08255, 25.2881, 20.618929);
((GeneralPath)shape).lineTo(24.806309, 19.472803);
((GeneralPath)shape).lineTo(25.240158, 18.818813);
((GeneralPath)shape).lineTo(24.999264, 18.546223);
((GeneralPath)shape).lineTo(25.528929, 17.290625);
((GeneralPath)shape).curveTo(25.964106, 16.74929, 26.418644, 16.229969, 26.87842, 15.708726);
((GeneralPath)shape).lineTo(27.698177, 15.490508);
((GeneralPath)shape).lineTo(28.61382, 15.381697);
((GeneralPath)shape).lineTo(29.240623, 15.545543);
((GeneralPath)shape).lineTo(30.13226, 16.444939);
((GeneralPath)shape).lineTo(30.445696, 16.090725);
((GeneralPath)shape).lineTo(30.878883, 16.03635);
((GeneralPath)shape).lineTo(31.698643, 16.308943);
((GeneralPath)shape).lineTo(32.325447, 16.308943);
((GeneralPath)shape).lineTo(32.759296, 15.926878);
((GeneralPath)shape).lineTo(32.95225, 15.654288);
((GeneralPath)shape).lineTo(32.51774, 15.381697);
((GeneralPath)shape).lineTo(31.794456, 15.327325);
((GeneralPath)shape).curveTo(31.593744, 15.0489, 31.40722, 14.756219, 31.168913, 14.508826);
((GeneralPath)shape).lineTo(30.927355, 14.617636);
((GeneralPath)shape).lineTo(30.830875, 15.327325);
((GeneralPath)shape).lineTo(30.397026, 14.836517);
((GeneralPath)shape).lineTo(30.301212, 14.290011);
((GeneralPath)shape).lineTo(29.819422, 13.909273);
((GeneralPath)shape).lineTo(29.625805, 13.909273);
((GeneralPath)shape).lineTo(30.10819, 14.45452);
((GeneralPath)shape).lineTo(29.915236, 14.945328);
((GeneralPath)shape).lineTo(29.529922, 15.054138);
((GeneralPath)shape).lineTo(29.770819, 14.56333);
((GeneralPath)shape).lineTo(29.336306, 14.345775);
((GeneralPath)shape).lineTo(28.951654, 13.909339);
((GeneralPath)shape).lineTo(28.22771, 14.072522);
((GeneralPath)shape).lineTo(28.131895, 14.290077);
((GeneralPath)shape).lineTo(27.698046, 14.56333);
((GeneralPath)shape).lineTo(27.457151, 15.163612);
((GeneralPath)shape).lineTo(26.854946, 15.463388);
((GeneralPath)shape).lineTo(26.58945, 15.163612);
((GeneralPath)shape).lineTo(26.300682, 15.163612);
((GeneralPath)shape).lineTo(26.300682, 14.181333);
((GeneralPath)shape).lineTo(26.927486, 13.853707);
((GeneralPath)shape).lineTo(27.409277, 13.853707);
((GeneralPath)shape).lineTo(27.312136, 13.472307);
((GeneralPath)shape).lineTo(26.927486, 13.090243);
((GeneralPath)shape).lineTo(27.577631, 12.953583);
((GeneralPath)shape).lineTo(27.93894, 12.544996);
((GeneralPath)shape).lineTo(28.22771, 12.053525);
((GeneralPath)shape).lineTo(28.758701, 12.053525);
((GeneralPath)shape).lineTo(28.61362, 11.672124);
((GeneralPath)shape).lineTo(28.951654, 11.453906);
((GeneralPath)shape).lineTo(28.951654, 11.890342);
((GeneralPath)shape).lineTo(29.674274, 12.053525);
((GeneralPath)shape).lineTo(30.396894, 11.453906);
((GeneralPath)shape).lineTo(30.44543, 11.180653);
((GeneralPath)shape).lineTo(31.071571, 10.744549);
((GeneralPath)shape).curveTo(30.844933, 10.772729, 30.618294, 10.793417, 30.396828, 10.853691);
((GeneralPath)shape).lineTo(30.396828, 10.362286);
((GeneralPath)shape).lineTo(30.637722, 9.816708);
((GeneralPath)shape).lineTo(30.396828, 9.816708);
((GeneralPath)shape).lineTo(29.867428, 10.307516);
((GeneralPath)shape).lineTo(29.722347, 10.580438);
((GeneralPath)shape).lineTo(29.867428, 10.962833);
((GeneralPath)shape).lineTo(29.62587, 11.616824);
((GeneralPath)shape).lineTo(29.240557, 11.398606);
((GeneralPath)shape).lineTo(28.903849, 11.017205);
((GeneralPath)shape).lineTo(28.372856, 11.398606);
((GeneralPath)shape).lineTo(28.179901, 10.526066);
((GeneralPath)shape).lineTo(29.095543, 9.926115);
((GeneralPath)shape).lineTo(29.095543, 9.59849);
((GeneralPath)shape).lineTo(29.674406, 9.216758);
((GeneralPath)shape).lineTo(30.590048, 8.998208);
((GeneralPath)shape).lineTo(31.216852, 9.216758);
((GeneralPath)shape).lineTo(32.37332, 9.434976);
((GeneralPath)shape).lineTo(32.084553, 9.762004);
((GeneralPath)shape).lineTo(31.457747, 9.762004);
((GeneralPath)shape).lineTo(32.084553, 10.416658);
((GeneralPath)shape).lineTo(32.56634, 9.871411);
((GeneralPath)shape).lineTo(32.712685, 9.631511);
((GeneralPath)shape).curveTo(32.712685, 9.631511, 34.560738, 11.287872, 35.616886, 13.099725);
((GeneralPath)shape).curveTo(36.67303, 14.912174, 37.16901, 17.048403, 37.16901, 17.482252);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_8;
g.setTransform(defaultTransform__0_8);
g.setClip(clip__0_8);
float alpha__0_9 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_9 = g.getClip();
AffineTransform defaultTransform__0_9 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_9 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.232998, 9.871411);
((GeneralPath)shape).lineTo(25.184462, 10.19844);
((GeneralPath)shape).lineTo(25.522497, 10.416658);
((GeneralPath)shape).lineTo(26.100101, 10.034926);
((GeneralPath)shape).lineTo(25.811333, 9.707566);
((GeneralPath)shape).lineTo(25.425423, 9.926115);
((GeneralPath)shape).lineTo(25.233131, 9.871411);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_9;
g.setTransform(defaultTransform__0_9);
g.setClip(clip__0_9);
float alpha__0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_10 = g.getClip();
AffineTransform defaultTransform__0_10 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_10 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.763327, 7.6348596);
((GeneralPath)shape).lineTo(24.509716, 7.14372);
((GeneralPath)shape).lineTo(23.064411, 7.3072343);
((GeneralPath)shape).lineTo(21.280474, 7.7980423);
((GeneralPath)shape).lineTo(20.943102, 8.125668);
((GeneralPath)shape).lineTo(22.051697, 8.8891325);
((GeneralPath)shape).lineTo(22.051697, 9.325568);
((GeneralPath)shape).lineTo(21.617847, 9.762004);
((GeneralPath)shape).lineTo(22.19671, 10.908461);
((GeneralPath)shape).lineTo(22.581362, 10.68958);
((GeneralPath)shape).lineTo(23.064411, 9.926115);
((GeneralPath)shape).curveTo(23.809046, 9.695896, 24.476694, 9.434976, 25.184462, 9.107615);
((GeneralPath)shape).lineTo(25.763327, 7.6347933);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_10;
g.setTransform(defaultTransform__0_10);
g.setClip(clip__0_10);
float alpha__0_11 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_11 = g.getClip();
AffineTransform defaultTransform__0_11 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_11 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.064875, 12.217769);
((GeneralPath)shape).lineTo(26.871922, 11.726298);
((GeneralPath)shape).lineTo(26.533884, 11.835705);
((GeneralPath)shape).lineTo(26.631025, 12.435324);
((GeneralPath)shape).lineTo(27.064875, 12.217769);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_11;
g.setTransform(defaultTransform__0_11);
g.setClip(clip__0_11);
float alpha__0_12 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_12 = g.getClip();
AffineTransform defaultTransform__0_12 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_12 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.257168, 12.107698);
((GeneralPath)shape).lineTo(27.16069, 12.763015);
((GeneralPath)shape).lineTo(27.691017, 12.653608);
((GeneralPath)shape).lineTo(28.07633, 12.272207);
((GeneralPath)shape).lineTo(27.738958, 11.944582);
((GeneralPath)shape).curveTo(27.625637, 11.642816, 27.495476, 11.361142, 27.353645, 11.07171);
((GeneralPath)shape).lineTo(27.064875, 11.07171);
((GeneralPath)shape).lineTo(27.064875, 11.398739);
((GeneralPath)shape).lineTo(27.257168, 11.616957);
((GeneralPath)shape).lineTo(27.257168, 12.107765);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_12;
g.setTransform(defaultTransform__0_12);
g.setClip(clip__0_12);
float alpha__0_13 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_13 = g.getClip();
AffineTransform defaultTransform__0_13 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_13 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.12394, 22.47375);
((GeneralPath)shape).lineTo(19.73803, 21.709686);
((GeneralPath)shape).lineTo(19.01508, 21.546173);
((GeneralPath)shape).lineTo(18.629501, 20.510118);
((GeneralPath)shape).lineTo(17.665655, 20.618929);
((GeneralPath)shape).lineTo(16.846493, 20.01931);
((GeneralPath)shape).lineTo(15.97846, 20.782776);
((GeneralPath)shape).lineTo(15.97846, 20.903189);
((GeneralPath)shape).curveTo(15.715883, 20.8274, 15.393098, 20.817057, 15.159298, 20.673302);
((GeneralPath)shape).lineTo(14.966343, 20.128056);
((GeneralPath)shape).lineTo(14.966343, 19.527773);
((GeneralPath)shape).lineTo(14.388075, 19.582146);
((GeneralPath)shape).curveTo(14.436347, 19.20008, 14.484221, 18.81868, 14.532824, 18.436684);
((GeneralPath)shape).lineTo(14.19512, 18.436684);
((GeneralPath)shape).lineTo(13.858079, 18.87312);
((GeneralPath)shape).lineTo(13.520375, 19.036303);
((GeneralPath)shape).lineTo(13.038253, 18.764309);
((GeneralPath)shape).lineTo(12.989981, 18.164026);
((GeneralPath)shape).lineTo(13.086459, 17.509373);
((GeneralPath)shape).lineTo(13.809741, 16.964127);
((GeneralPath)shape).lineTo(14.388009, 16.964127);
((GeneralPath)shape).lineTo(14.484155, 16.636501);
((GeneralPath)shape).lineTo(15.207105, 16.799683);
((GeneralPath)shape).lineTo(15.737433, 17.455);
((GeneralPath)shape).lineTo(15.83391, 16.363247);
((GeneralPath)shape).lineTo(16.749817, 15.599783);
((GeneralPath)shape).lineTo(17.087189, 14.781283);
((GeneralPath)shape).lineTo(17.761932, 14.508693);
((GeneralPath)shape).lineTo(18.14751, 13.963446);
((GeneralPath)shape).lineTo(19.01488, 13.799004);
((GeneralPath)shape).lineTo(19.44906, 13.145013);
((GeneralPath)shape).curveTo(19.015211, 13.145013, 18.581362, 13.145013, 18.14751, 13.145013);
((GeneralPath)shape).lineTo(18.96694, 12.762949);
((GeneralPath)shape).lineTo(19.544876, 12.762949);
((GeneralPath)shape).lineTo(20.364635, 12.489696);
((GeneralPath)shape).lineTo(20.461113, 12.16333);
((GeneralPath)shape).lineTo(20.17168, 11.890077);
((GeneralPath)shape).lineTo(19.834309, 11.78067);
((GeneralPath)shape).lineTo(19.930784, 11.453641);
((GeneralPath)shape).lineTo(19.68989, 10.962833);
((GeneralPath)shape).lineTo(19.111292, 11.180388);
((GeneralPath)shape).lineTo(19.207767, 10.744284);
((GeneralPath)shape).lineTo(18.533024, 10.36222);
((GeneralPath)shape).lineTo(18.003027, 11.289132);
((GeneralPath)shape).lineTo(18.050968, 11.616758);
((GeneralPath)shape).lineTo(17.520971, 11.835639);
((GeneralPath)shape).lineTo(17.183268, 12.544665);
((GeneralPath)shape).lineTo(17.03885, 11.890011);
((GeneralPath)shape).lineTo(16.122944, 11.507947);
((GeneralPath)shape).lineTo(15.978195, 11.017139);
((GeneralPath)shape).lineTo(17.183268, 10.30745);
((GeneralPath)shape).lineTo(17.713594, 9.816642);
((GeneralPath)shape).lineTo(17.761868, 9.216692);
((GeneralPath)shape).lineTo(17.472767, 9.052846);
((GeneralPath)shape).lineTo(17.087189, 8.998142);
((GeneralPath)shape).lineTo(16.846292, 9.598424);
((GeneralPath)shape).curveTo(16.846292, 9.598424, 16.44321, 9.677396, 16.339571, 9.702991);
((GeneralPath)shape).curveTo(15.016007, 10.922651, 12.341693, 13.555523, 11.720392, 18.526);
((GeneralPath)shape).curveTo(11.744992, 18.641241, 12.170752, 19.309488, 12.170752, 19.309488);
((GeneralPath)shape).lineTo(13.18287, 19.909107);
((GeneralPath)shape).lineTo(14.194988, 20.18236);
((GeneralPath)shape).lineTo(14.629169, 20.728205);
((GeneralPath)shape).lineTo(15.303583, 21.219011);
((GeneralPath)shape).lineTo(15.689161, 21.16464);
((GeneralPath)shape).lineTo(15.978261, 21.294802);
((GeneralPath)shape).lineTo(15.978261, 21.382858);
((GeneralPath)shape).lineTo(15.592949, 22.419245);
((GeneralPath)shape).lineTo(15.303516, 22.85568);
((GeneralPath)shape).lineTo(15.399994, 23.07456);
((GeneralPath)shape).lineTo(15.159099, 23.891735);
((GeneralPath)shape).lineTo(16.026798, 25.474297);
((GeneralPath)shape).lineTo(16.894167, 26.238358);
((GeneralPath)shape).lineTo(17.280077, 26.783604);
((GeneralPath)shape).lineTo(17.23154, 27.92973);
((GeneralPath)shape).lineTo(17.520971, 28.58372);
((GeneralPath)shape).lineTo(17.23154, 29.838655);
((GeneralPath)shape).curveTo(17.23154, 29.838655, 17.208862, 29.830896, 17.245796, 29.956484);
((GeneralPath)shape).curveTo(17.28306, 30.082136, 18.790165, 30.918737, 18.885979, 30.847525);
((GeneralPath)shape).curveTo(18.98146, 30.774984, 19.063086, 30.711527, 19.063086, 30.711527);
((GeneralPath)shape).lineTo(18.96694, 30.439533);
((GeneralPath)shape).lineTo(19.352251, 30.05747);
((GeneralPath)shape).lineTo(19.497002, 29.675406);
((GeneralPath)shape).lineTo(20.123806, 29.456526);
((GeneralPath)shape).lineTo(20.605597, 28.256027);
((GeneralPath)shape).lineTo(20.46118, 27.929663);
((GeneralPath)shape).lineTo(20.797888, 27.438854);
((GeneralPath)shape).lineTo(21.521172, 27.274412);
((GeneralPath)shape).lineTo(21.90708, 26.40154);
((GeneralPath)shape).lineTo(21.810602, 25.311113);
((GeneralPath)shape).lineTo(22.38887, 24.492613);
((GeneralPath)shape).lineTo(22.485348, 23.674112);
((GeneralPath)shape).curveTo(21.694035, 23.281706, 20.90922, 22.877628, 20.123806, 22.473616);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_13;
g.setTransform(defaultTransform__0_13);
g.setClip(clip__0_13);
float alpha__0_14 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_14 = g.getClip();
AffineTransform defaultTransform__0_14 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_14 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.063284, 10.089298);
((GeneralPath)shape).lineTo(19.545074, 10.416658);
((GeneralPath)shape).lineTo(19.930984, 10.416658);
((GeneralPath)shape).lineTo(19.930984, 10.034926);
((GeneralPath)shape).lineTo(19.449194, 9.816708);
((GeneralPath)shape).lineTo(19.063284, 10.089298);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_14;
g.setTransform(defaultTransform__0_14);
g.setClip(clip__0_14);
float alpha__0_15 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_15 = g.getClip();
AffineTransform defaultTransform__0_15 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_15 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.810337, 9.653193);
((GeneralPath)shape).lineTo(17.56911, 10.253144);
((GeneralPath)shape).lineTo(18.051233, 10.253144);
((GeneralPath)shape).lineTo(18.29246, 9.707566);
((GeneralPath)shape).curveTo(18.500334, 9.560562, 18.707212, 9.41263, 18.918934, 9.27113);
((GeneralPath)shape).lineTo(19.401054, 9.434976);
((GeneralPath)shape).curveTo(19.722248, 9.653193, 20.043442, 9.871411, 20.3649, 10.089298);
((GeneralPath)shape).lineTo(20.847288, 9.653193);
((GeneralPath)shape).lineTo(20.31663, 9.434976);
((GeneralPath)shape).lineTo(20.075401, 8.943836);
((GeneralPath)shape).lineTo(19.159761, 8.834694);
((GeneralPath)shape).lineTo(19.11149, 8.561772);
((GeneralPath)shape).lineTo(18.67764, 8.67118);
((GeneralPath)shape).lineTo(18.485083, 9.0529785);
((GeneralPath)shape).lineTo(18.243856, 8.561838);
((GeneralPath)shape).lineTo(18.14771, 8.780057);
((GeneralPath)shape).lineTo(18.195982, 9.325635);
((GeneralPath)shape).lineTo(17.810337, 9.653193);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_15;
g.setTransform(defaultTransform__0_15);
g.setClip(clip__0_15);
float alpha__0_16 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_16 = g.getClip();
AffineTransform defaultTransform__0_16 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_16 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.545074, 8.288851);
((GeneralPath)shape).lineTo(19.7863, 8.070964);
((GeneralPath)shape).lineTo(20.268423, 7.961822);
((GeneralPath)shape).curveTo(20.598635, 7.801225, 20.930172, 7.6930776, 21.28054, 7.579758);
((GeneralPath)shape).lineTo(21.08825, 7.252398);
((GeneralPath)shape).lineTo(20.465954, 7.3417807);
((GeneralPath)shape).lineTo(20.171946, 7.6347933);
((GeneralPath)shape).lineTo(19.687239, 7.7050796);
((GeneralPath)shape).lineTo(19.256306, 7.9074497);
((GeneralPath)shape).lineTo(19.04684, 8.008768);
((GeneralPath)shape).lineTo(18.918934, 8.18004);
((GeneralPath)shape).lineTo(19.545074, 8.288851);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_16;
g.setTransform(defaultTransform__0_16);
g.setClip(clip__0_16);
float alpha__0_17 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_17 = g.getClip();
AffineTransform defaultTransform__0_17 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_17 is ShapeNode
paint = new Color(0, 0, 0, 182);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.364834, 13.472041);
((GeneralPath)shape).lineTo(20.654266, 13.035606);
((GeneralPath)shape).lineTo(20.220085, 12.708577);
((GeneralPath)shape).lineTo(20.364834, 13.472041);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_17;
g.setTransform(defaultTransform__0_17);
g.setClip(clip__0_17);
float alpha__0_18 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_18 = g.getClip();
AffineTransform defaultTransform__0_18 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_18 is ShapeNode
paint = new Color(255, 255, 255, 245);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(28.188967, 36.935368);
((GeneralPath)shape).lineTo(19.188967, 36.935368);
((GeneralPath)shape).curveTo(18.911966, 36.935368, 18.688967, 37.158367, 18.688967, 37.435368);
((GeneralPath)shape).curveTo(18.688967, 37.712368, 18.911966, 37.935368, 19.188967, 37.935368);
((GeneralPath)shape).lineTo(28.188967, 37.935368);
((GeneralPath)shape).curveTo(28.465967, 37.935368, 28.688967, 37.712368, 28.688967, 37.435368);
((GeneralPath)shape).curveTo(28.688967, 37.158367, 28.465967, 36.935368, 28.188967, 36.935368);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_18;
g.setTransform(defaultTransform__0_18);
g.setClip(clip__0_18);
float alpha__0_19 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_19 = g.getClip();
AffineTransform defaultTransform__0_19 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_19 is ShapeNode
paint = new Color(255, 255, 255, 245);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(29.063961, 37.466633);
((GeneralPath)shape).curveTo(29.063961, 38.070694, 29.55365, 38.560383, 30.157711, 38.560383);
((GeneralPath)shape).curveTo(30.761772, 38.560383, 31.251461, 38.070694, 31.251461, 37.466633);
((GeneralPath)shape).curveTo(31.251461, 36.86257, 30.761772, 36.372883, 30.157711, 36.372883);
((GeneralPath)shape).curveTo(29.55365, 36.372883, 29.063961, 36.86257, 29.063961, 37.466633);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_19;
g.setTransform(defaultTransform__0_19);
g.setClip(clip__0_19);
float alpha__0_20 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_20 = g.getClip();
AffineTransform defaultTransform__0_20 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_20 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(37.032646, 17.345886);
((GeneralPath)shape).curveTo(37.032646, 17.520077, 37.032646, 17.345886, 37.032646, 17.345886);
((GeneralPath)shape).lineTo(36.671337, 17.755136);
((GeneralPath)shape).curveTo(36.449867, 17.49415, 36.201218, 17.274673, 35.948715, 17.045446);
((GeneralPath)shape).lineTo(35.39445, 17.127007);
((GeneralPath)shape).lineTo(34.88806, 16.554573);
((GeneralPath)shape).lineTo(34.88806, 17.263002);
((GeneralPath)shape).lineTo(35.32191, 17.591291);
((GeneralPath)shape).lineTo(35.61068, 17.91832);
((GeneralPath)shape).lineTo(35.99659, 17.481882);
((GeneralPath)shape).curveTo(36.09373, 17.663832, 36.189545, 17.84578, 36.286022, 18.027727);
((GeneralPath)shape).lineTo(36.286022, 18.572973);
((GeneralPath)shape).lineTo(35.85151, 19.063782);
((GeneralPath)shape).lineTo(35.05635, 19.609625);
((GeneralPath)shape).lineTo(34.454144, 20.21057);
((GeneralPath)shape).lineTo(34.068237, 19.772808);
((GeneralPath)shape).lineTo(34.261192, 19.282);
((GeneralPath)shape).lineTo(33.875877, 18.845564);
((GeneralPath)shape).lineTo(33.225136, 17.454632);
((GeneralPath)shape).lineTo(32.67087, 16.827826);
((GeneralPath)shape).lineTo(32.52579, 16.991009);
((GeneralPath)shape).lineTo(32.743347, 17.782324);
((GeneralPath)shape).lineTo(33.152596, 18.245945);
((GeneralPath)shape).curveTo(33.38633, 18.92069, 33.617542, 19.565598, 33.92448, 20.21057);
((GeneralPath)shape).curveTo(34.400436, 20.21057, 34.84914, 20.160044, 35.321846, 20.100498);
((GeneralPath)shape).lineTo(35.321846, 20.482563);
((GeneralPath)shape).lineTo(34.74358, 21.901012);
((GeneralPath)shape).lineTo(34.21325, 22.500631);
((GeneralPath)shape).lineTo(33.7794, 23.429201);
((GeneralPath)shape).curveTo(33.7794, 23.938177, 33.7794, 24.447155, 33.7794, 24.956064);
((GeneralPath)shape).lineTo(33.92448, 25.55701);
((GeneralPath)shape).lineTo(33.683586, 25.829002);
((GeneralPath)shape).lineTo(33.152596, 26.156628);
((GeneralPath)shape).lineTo(32.59833, 26.62025);
((GeneralPath)shape).lineTo(33.05678, 27.138311);
((GeneralPath)shape).lineTo(32.429977, 27.684816);
((GeneralPath)shape).lineTo(32.550392, 28.038368);
((GeneralPath)shape).lineTo(31.610151, 29.102936);
((GeneralPath)shape).lineTo(30.984009, 29.102936);
((GeneralPath)shape).lineTo(30.453682, 29.430561);
((GeneralPath)shape).lineTo(30.115646, 29.430561);
((GeneralPath)shape).lineTo(30.115646, 28.994125);
((GeneralPath)shape).lineTo(29.971891, 28.119926);
((GeneralPath)shape).curveTo(29.785368, 27.572096, 29.591152, 27.028173, 29.393623, 26.484255);
((GeneralPath)shape).curveTo(29.393623, 26.082762, 29.41756, 25.685183, 29.441565, 25.283756);
((GeneralPath)shape).lineTo(29.683123, 24.73851);
((GeneralPath)shape).lineTo(29.345085, 24.083193);
((GeneralPath)shape).lineTo(29.369686, 23.183134);
((GeneralPath)shape).lineTo(28.911236, 22.665073);
((GeneralPath)shape).lineTo(29.140461, 21.915203);
((GeneralPath)shape).lineTo(28.767483, 21.492027);
((GeneralPath)shape).lineTo(28.116077, 21.492027);
((GeneralPath)shape).lineTo(27.899185, 21.246624);
((GeneralPath)shape).lineTo(27.248444, 21.656206);
((GeneralPath)shape).lineTo(26.98361, 21.355434);
((GeneralPath)shape).lineTo(26.380743, 21.87376);
((GeneralPath)shape).curveTo(25.971493, 21.409807, 25.56158, 20.946184, 25.151733, 20.482563);
((GeneralPath)shape).lineTo(24.669943, 19.336437);
((GeneralPath)shape).lineTo(25.103792, 18.682447);
((GeneralPath)shape).lineTo(24.862898, 18.409857);
((GeneralPath)shape).lineTo(25.392563, 17.154259);
((GeneralPath)shape).curveTo(25.82774, 16.612925, 26.282276, 16.093603, 26.742052, 15.57236);
((GeneralPath)shape).lineTo(27.561811, 15.354142);
((GeneralPath)shape).lineTo(28.477453, 15.245331);
((GeneralPath)shape).lineTo(29.104258, 15.409177);
((GeneralPath)shape).lineTo(29.995893, 16.308573);
((GeneralPath)shape).lineTo(30.30933, 15.954358);
((GeneralPath)shape).lineTo(30.742517, 15.899985);
((GeneralPath)shape).lineTo(31.562277, 16.172577);
((GeneralPath)shape).lineTo(32.18908, 16.172577);
((GeneralPath)shape).lineTo(32.622932, 15.790512);
((GeneralPath)shape).lineTo(32.815887, 15.517922);
((GeneralPath)shape).lineTo(32.381374, 15.245331);
((GeneralPath)shape).lineTo(31.65809, 15.190959);
((GeneralPath)shape).curveTo(31.457378, 14.912534, 31.270855, 14.619853, 31.032545, 14.37246);
((GeneralPath)shape).lineTo(30.790989, 14.48127);
((GeneralPath)shape).lineTo(30.69451, 15.190959);
((GeneralPath)shape).lineTo(30.26066, 14.700151);
((GeneralPath)shape).lineTo(30.164846, 14.153645);
((GeneralPath)shape).lineTo(29.683056, 13.772907);
((GeneralPath)shape).lineTo(29.489437, 13.772907);
((GeneralPath)shape).lineTo(29.971825, 14.318154);
((GeneralPath)shape).lineTo(29.77887, 14.808962);
((GeneralPath)shape).lineTo(29.393557, 14.917772);
((GeneralPath)shape).lineTo(29.634453, 14.426964);
((GeneralPath)shape).lineTo(29.19994, 14.209409);
((GeneralPath)shape).lineTo(28.815289, 13.772973);
((GeneralPath)shape).lineTo(28.091345, 13.936156);
((GeneralPath)shape).lineTo(27.99553, 14.153711);
((GeneralPath)shape).lineTo(27.56168, 14.426964);
((GeneralPath)shape).lineTo(27.320786, 15.027246);
((GeneralPath)shape).lineTo(26.71858, 15.327022);
((GeneralPath)shape).lineTo(26.453085, 15.027246);
((GeneralPath)shape).lineTo(26.164316, 15.027246);
((GeneralPath)shape).lineTo(26.164316, 14.044967);
((GeneralPath)shape).lineTo(26.79112, 13.717341);
((GeneralPath)shape).lineTo(27.272911, 13.717341);
((GeneralPath)shape).lineTo(27.17577, 13.335941);
((GeneralPath)shape).lineTo(26.79112, 12.953877);
((GeneralPath)shape).lineTo(27.441265, 12.817217);
((GeneralPath)shape).lineTo(27.802574, 12.40863);
((GeneralPath)shape).lineTo(28.091345, 11.917159);
((GeneralPath)shape).lineTo(28.622335, 11.917159);
((GeneralPath)shape).lineTo(28.477255, 11.535758);
((GeneralPath)shape).lineTo(28.815289, 11.31754);
((GeneralPath)shape).lineTo(28.815289, 11.753976);
((GeneralPath)shape).lineTo(29.537909, 11.917159);
((GeneralPath)shape).lineTo(30.260529, 11.31754);
((GeneralPath)shape).lineTo(30.309065, 11.044287);
((GeneralPath)shape).lineTo(30.935205, 10.608183);
((GeneralPath)shape).curveTo(30.708567, 10.636363, 30.481928, 10.657051, 30.260462, 10.717325);
((GeneralPath)shape).lineTo(30.260462, 10.22592);
((GeneralPath)shape).lineTo(30.501356, 9.680342);
((GeneralPath)shape).lineTo(30.260462, 9.680342);
((GeneralPath)shape).lineTo(29.731062, 10.17115);
((GeneralPath)shape).lineTo(29.585981, 10.444072);
((GeneralPath)shape).lineTo(29.731062, 10.826467);
((GeneralPath)shape).lineTo(29.489504, 11.480458);
((GeneralPath)shape).lineTo(29.10419, 11.26224);
((GeneralPath)shape).lineTo(28.767483, 10.880839);
((GeneralPath)shape).lineTo(28.23649, 11.26224);
((GeneralPath)shape).lineTo(28.043535, 10.3897);
((GeneralPath)shape).lineTo(28.959177, 9.789749);
((GeneralPath)shape).lineTo(28.959177, 9.462124);
((GeneralPath)shape).lineTo(29.53804, 9.080392);
((GeneralPath)shape).lineTo(30.453682, 8.861842);
((GeneralPath)shape).lineTo(31.080486, 9.080392);
((GeneralPath)shape).lineTo(32.236954, 9.29861);
((GeneralPath)shape).lineTo(31.948187, 9.625638);
((GeneralPath)shape).lineTo(31.32138, 9.625638);
((GeneralPath)shape).lineTo(31.948187, 10.280292);
((GeneralPath)shape).lineTo(32.429977, 9.735045);
((GeneralPath)shape).lineTo(32.576317, 9.495145);
((GeneralPath)shape).curveTo(32.576317, 9.495145, 34.424374, 11.151506, 35.48052, 12.963359);
((GeneralPath)shape).curveTo(36.536667, 14.775808, 37.032646, 16.912037, 37.032646, 17.345886);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_20;
g.setTransform(defaultTransform__0_20);
g.setClip(clip__0_20);
float alpha__0_21 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_21 = g.getClip();
AffineTransform defaultTransform__0_21 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_21 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.096632, 9.735045);
((GeneralPath)shape).lineTo(25.048096, 10.062074);
((GeneralPath)shape).lineTo(25.386131, 10.280292);
((GeneralPath)shape).lineTo(25.963736, 9.89856);
((GeneralPath)shape).lineTo(25.674967, 9.5712);
((GeneralPath)shape).lineTo(25.289057, 9.789749);
((GeneralPath)shape).lineTo(25.096766, 9.735045);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_21;
g.setTransform(defaultTransform__0_21);
g.setClip(clip__0_21);
float alpha__0_22 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_22 = g.getClip();
AffineTransform defaultTransform__0_22 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_22 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(25.62696, 7.4984937);
((GeneralPath)shape).lineTo(24.37335, 7.007354);
((GeneralPath)shape).lineTo(22.928045, 7.1708684);
((GeneralPath)shape).lineTo(21.144108, 7.6616764);
((GeneralPath)shape).lineTo(20.806736, 7.9893017);
((GeneralPath)shape).lineTo(21.91533, 8.752767);
((GeneralPath)shape).lineTo(21.91533, 9.189202);
((GeneralPath)shape).lineTo(21.481482, 9.625638);
((GeneralPath)shape).lineTo(22.060345, 10.772095);
((GeneralPath)shape).lineTo(22.444996, 10.553214);
((GeneralPath)shape).lineTo(22.928045, 9.789749);
((GeneralPath)shape).curveTo(23.67268, 9.55953, 24.340328, 9.29861, 25.048096, 8.97125);
((GeneralPath)shape).lineTo(25.62696, 7.4984274);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_22;
g.setTransform(defaultTransform__0_22);
g.setClip(clip__0_22);
float alpha__0_23 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_23 = g.getClip();
AffineTransform defaultTransform__0_23 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_23 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(26.928509, 12.081403);
((GeneralPath)shape).lineTo(26.735556, 11.589932);
((GeneralPath)shape).lineTo(26.397518, 11.699339);
((GeneralPath)shape).lineTo(26.49466, 12.298958);
((GeneralPath)shape).lineTo(26.928509, 12.081403);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_23;
g.setTransform(defaultTransform__0_23);
g.setClip(clip__0_23);
float alpha__0_24 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_24 = g.getClip();
AffineTransform defaultTransform__0_24 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_24 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(27.120802, 11.971332);
((GeneralPath)shape).lineTo(27.024324, 12.626649);
((GeneralPath)shape).lineTo(27.554651, 12.517242);
((GeneralPath)shape).lineTo(27.939964, 12.135841);
((GeneralPath)shape).lineTo(27.602592, 11.808216);
((GeneralPath)shape).curveTo(27.489271, 11.50645, 27.35911, 11.224776, 27.21728, 10.935344);
((GeneralPath)shape).lineTo(26.928509, 10.935344);
((GeneralPath)shape).lineTo(26.928509, 11.262373);
((GeneralPath)shape).lineTo(27.120802, 11.480591);
((GeneralPath)shape).lineTo(27.120802, 11.971399);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_24;
g.setTransform(defaultTransform__0_24);
g.setClip(clip__0_24);
float alpha__0_25 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_25 = g.getClip();
AffineTransform defaultTransform__0_25 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_25 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.987574, 22.337383);
((GeneralPath)shape).lineTo(19.601664, 21.57332);
((GeneralPath)shape).lineTo(18.878714, 21.409807);
((GeneralPath)shape).lineTo(18.493135, 20.373753);
((GeneralPath)shape).lineTo(17.52929, 20.482563);
((GeneralPath)shape).lineTo(16.710127, 19.882944);
((GeneralPath)shape).lineTo(15.842094, 20.646408);
((GeneralPath)shape).lineTo(15.842094, 20.766823);
((GeneralPath)shape).curveTo(15.579517, 20.691034, 15.256732, 20.68069, 15.022932, 20.536936);
((GeneralPath)shape).lineTo(14.829977, 19.99169);
((GeneralPath)shape).lineTo(14.829977, 19.391407);
((GeneralPath)shape).lineTo(14.251709, 19.44578);
((GeneralPath)shape).curveTo(14.299981, 19.063715, 14.347855, 18.682314, 14.396458, 18.300318);
((GeneralPath)shape).lineTo(14.058754, 18.300318);
((GeneralPath)shape).lineTo(13.721713, 18.736753);
((GeneralPath)shape).lineTo(13.384009, 18.899937);
((GeneralPath)shape).lineTo(12.901887, 18.627941);
((GeneralPath)shape).lineTo(12.853615, 18.02766);
((GeneralPath)shape).lineTo(12.950093, 17.373007);
((GeneralPath)shape).lineTo(13.673375, 16.82776);
((GeneralPath)shape).lineTo(14.251643, 16.82776);
((GeneralPath)shape).lineTo(14.347789, 16.500135);
((GeneralPath)shape).lineTo(15.070739, 16.663317);
((GeneralPath)shape).lineTo(15.601067, 17.318634);
((GeneralPath)shape).lineTo(15.697544, 16.226881);
((GeneralPath)shape).lineTo(16.61345, 15.463417);
((GeneralPath)shape).lineTo(16.950823, 14.644917);
((GeneralPath)shape).lineTo(17.625566, 14.372327);
((GeneralPath)shape).lineTo(18.011145, 13.82708);
((GeneralPath)shape).lineTo(18.878513, 13.662638);
((GeneralPath)shape).lineTo(19.312695, 13.008647);
((GeneralPath)shape).curveTo(18.878845, 13.008647, 18.444996, 13.008647, 18.011145, 13.008647);
((GeneralPath)shape).lineTo(18.830572, 12.626583);
((GeneralPath)shape).lineTo(19.40851, 12.626583);
((GeneralPath)shape).lineTo(20.22827, 12.35333);
((GeneralPath)shape).lineTo(20.324747, 12.026964);
((GeneralPath)shape).lineTo(20.035315, 11.753711);
((GeneralPath)shape).lineTo(19.697943, 11.644304);
((GeneralPath)shape).lineTo(19.794418, 11.317275);
((GeneralPath)shape).lineTo(19.553524, 10.826467);
((GeneralPath)shape).lineTo(18.974924, 11.044022);
((GeneralPath)shape).lineTo(19.071402, 10.607918);
((GeneralPath)shape).lineTo(18.396658, 10.225854);
((GeneralPath)shape).lineTo(17.866661, 11.152766);
((GeneralPath)shape).lineTo(17.914602, 11.480392);
((GeneralPath)shape).lineTo(17.384605, 11.699273);
((GeneralPath)shape).lineTo(17.046902, 12.408299);
((GeneralPath)shape).lineTo(16.902485, 11.753645);
((GeneralPath)shape).lineTo(15.986578, 11.371581);
((GeneralPath)shape).lineTo(15.841829, 10.880773);
((GeneralPath)shape).lineTo(17.046902, 10.171084);
((GeneralPath)shape).lineTo(17.577229, 9.680276);
((GeneralPath)shape).lineTo(17.625502, 9.080325);
((GeneralPath)shape).lineTo(17.3364, 8.91648);
((GeneralPath)shape).lineTo(16.950823, 8.861776);
((GeneralPath)shape).lineTo(16.709927, 9.462058);
((GeneralPath)shape).curveTo(16.709927, 9.462058, 16.306845, 9.54103, 16.203205, 9.566625);
((GeneralPath)shape).curveTo(14.879641, 10.786285, 12.205327, 13.419157, 11.584026, 18.389633);
((GeneralPath)shape).curveTo(11.608626, 18.504875, 12.034386, 19.173122, 12.034386, 19.173122);
((GeneralPath)shape).lineTo(13.046504, 19.772741);
((GeneralPath)shape).lineTo(14.058622, 20.045994);
((GeneralPath)shape).lineTo(14.492803, 20.591839);
((GeneralPath)shape).lineTo(15.167217, 21.082645);
((GeneralPath)shape).lineTo(15.552795, 21.028275);
((GeneralPath)shape).lineTo(15.841895, 21.158436);
((GeneralPath)shape).lineTo(15.841895, 21.246492);
((GeneralPath)shape).lineTo(15.456583, 22.282879);
((GeneralPath)shape).lineTo(15.16715, 22.719315);
((GeneralPath)shape).lineTo(15.263628, 22.938194);
((GeneralPath)shape).lineTo(15.022733, 23.75537);
((GeneralPath)shape).lineTo(15.890433, 25.33793);
((GeneralPath)shape).lineTo(16.757801, 26.101992);
((GeneralPath)shape).lineTo(17.143711, 26.647238);
((GeneralPath)shape).lineTo(17.095175, 27.793364);
((GeneralPath)shape).lineTo(17.384605, 28.447353);
((GeneralPath)shape).lineTo(17.095175, 29.70229);
((GeneralPath)shape).curveTo(17.095175, 29.70229, 17.072496, 29.69453, 17.10943, 29.820118);
((GeneralPath)shape).curveTo(17.146694, 29.94577, 18.653797, 30.782372, 18.749613, 30.711159);
((GeneralPath)shape).curveTo(18.845095, 30.638618, 18.92672, 30.575161, 18.92672, 30.575161);
((GeneralPath)shape).lineTo(18.830572, 30.303167);
((GeneralPath)shape).lineTo(19.215885, 29.921104);
((GeneralPath)shape).lineTo(19.360636, 29.53904);
((GeneralPath)shape).lineTo(19.98744, 29.32016);
((GeneralPath)shape).lineTo(20.46923, 28.119661);
((GeneralPath)shape).lineTo(20.324814, 27.793297);
((GeneralPath)shape).lineTo(20.661522, 27.302488);
((GeneralPath)shape).lineTo(21.384806, 27.138046);
((GeneralPath)shape).lineTo(21.770714, 26.265175);
((GeneralPath)shape).lineTo(21.674236, 25.174747);
((GeneralPath)shape).lineTo(22.252504, 24.356247);
((GeneralPath)shape).lineTo(22.348982, 23.537746);
((GeneralPath)shape).curveTo(21.557669, 23.14534, 20.772854, 22.741262, 19.98744, 22.33725);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_25;
g.setTransform(defaultTransform__0_25);
g.setClip(clip__0_25);
float alpha__0_26 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_26 = g.getClip();
AffineTransform defaultTransform__0_26 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_26 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(18.926918, 9.952932);
((GeneralPath)shape).lineTo(19.408709, 10.280292);
((GeneralPath)shape).lineTo(19.794619, 10.280292);
((GeneralPath)shape).lineTo(19.794619, 9.89856);
((GeneralPath)shape).lineTo(19.312828, 9.680342);
((GeneralPath)shape).lineTo(18.926918, 9.952932);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_26;
g.setTransform(defaultTransform__0_26);
g.setClip(clip__0_26);
float alpha__0_27 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_27 = g.getClip();
AffineTransform defaultTransform__0_27 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_27 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(17.673971, 9.516828);
((GeneralPath)shape).lineTo(17.432745, 10.116778);
((GeneralPath)shape).lineTo(17.914867, 10.116778);
((GeneralPath)shape).lineTo(18.156094, 9.5712);
((GeneralPath)shape).curveTo(18.363968, 9.424196, 18.570847, 9.276264, 18.782566, 9.134764);
((GeneralPath)shape).lineTo(19.264688, 9.29861);
((GeneralPath)shape).curveTo(19.585882, 9.516828, 19.907076, 9.735045, 20.228535, 9.952932);
((GeneralPath)shape).lineTo(20.710922, 9.516828);
((GeneralPath)shape).lineTo(20.180264, 9.29861);
((GeneralPath)shape).lineTo(19.939035, 8.80747);
((GeneralPath)shape).lineTo(19.023396, 8.698328);
((GeneralPath)shape).lineTo(18.975124, 8.425406);
((GeneralPath)shape).lineTo(18.541273, 8.534814);
((GeneralPath)shape).lineTo(18.348717, 8.916613);
((GeneralPath)shape).lineTo(18.10749, 8.425472);
((GeneralPath)shape).lineTo(18.011345, 8.643691);
((GeneralPath)shape).lineTo(18.059616, 9.189269);
((GeneralPath)shape).lineTo(17.673971, 9.516828);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_27;
g.setTransform(defaultTransform__0_27);
g.setClip(clip__0_27);
float alpha__0_28 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_28 = g.getClip();
AffineTransform defaultTransform__0_28 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_28 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(19.408709, 8.152485);
((GeneralPath)shape).lineTo(19.649935, 7.934598);
((GeneralPath)shape).lineTo(20.132057, 7.825456);
((GeneralPath)shape).curveTo(20.462269, 7.6648593, 20.793806, 7.5567117, 21.144175, 7.4433923);
((GeneralPath)shape).lineTo(20.951883, 7.116032);
((GeneralPath)shape).lineTo(20.329588, 7.205415);
((GeneralPath)shape).lineTo(20.03558, 7.4984274);
((GeneralPath)shape).lineTo(19.550873, 7.568713);
((GeneralPath)shape).lineTo(19.11994, 7.771084);
((GeneralPath)shape).lineTo(18.910475, 7.8724017);
((GeneralPath)shape).lineTo(18.782566, 8.043674);
((GeneralPath)shape).lineTo(19.408709, 8.152485);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_28;
g.setTransform(defaultTransform__0_28);
g.setClip(clip__0_28);
float alpha__0_29 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_29 = g.getClip();
AffineTransform defaultTransform__0_29 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_29 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(18.633779525756836, 17.486207962036133), 40.692665f, new Point2D.Double(18.93430519104004, 17.810213088989258), new float[] {0.0f,0.37931034f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(254, 254, 254, 255),new Color(29, 29, 29, 255)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.687490701675415f, 0.0f, 0.0f, 0.6395266056060791f, 7.810068130493164f, 3.6106860637664795f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(20.228468, 13.335675);
((GeneralPath)shape).lineTo(20.5179, 12.89924);
((GeneralPath)shape).lineTo(20.08372, 12.572211);
((GeneralPath)shape).lineTo(20.228468, 13.335675);
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_29;
g.setTransform(defaultTransform__0_29);
g.setClip(clip__0_29);
float alpha__0_30 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_30 = g.getClip();
AffineTransform defaultTransform__0_30 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_30 is ShapeNode
paint = new RadialGradientPaint(new Point2D.Double(15.601279258728027, 12.142301559448242), 43.526714f, new Point2D.Double(15.601279258728027, 12.142301559448242), new float[] {0.0f,1.0f}, new Color[] {new Color(255, 255, 255, 255),new Color(255, 255, 255, 42)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.6749669909477234f, 0.0f, 0.0f, 0.6749809980392456f, 7.864706039428711f, 3.589838981628418f));
stroke = new BasicStroke(1.0f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(36.871773, 19.441963);
((GeneralPath)shape).curveTo(36.871773, 26.303627, 31.309195, 31.866144, 24.448475, 31.866144);
((GeneralPath)shape).curveTo(17.587126, 31.866144, 12.024859, 26.303564, 12.024859, 19.441963);
((GeneralPath)shape).curveTo(12.024859, 12.580614, 17.587126, 7.0186653, 24.448475, 7.0186653);
((GeneralPath)shape).curveTo(31.309195, 7.0186653, 36.871773, 12.580614, 36.871773, 19.441963);
((GeneralPath)shape).lineTo(36.871773, 19.441963);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_30;
g.setTransform(defaultTransform__0_30);
g.setClip(clip__0_30);
float alpha__0_31 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_31 = g.getClip();
AffineTransform defaultTransform__0_31 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_31 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(12.190512657165527, 12.062389373779297), new Point2D.Double(17.85013771057129, 31.674617767333984), new float[] {0.0f,1.0f}, new Color[] {new Color(20, 59, 104, 255),new Color(20, 59, 104, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(0.9501760005950928f, 0.0f, 0.0f, 1.2505500316619873f, -2.628602981567383f, -7.378110885620117f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(5.3935747, 10.941178);
((GeneralPath)shape).curveTo(0.8481934, 23.232819, 9.452054, 34.43391, 23.052696, 34.43391);
((GeneralPath)shape).curveTo(14.482897, 34.43391, 7.5108695, 27.461952, 7.5108695, 18.892088);
((GeneralPath)shape).curveTo(7.5108695, 12.874995, 11.030621, 7.3515954, 16.481428, 4.804075);
((GeneralPath)shape).lineTo(0.5397719, 8.693082);
((GeneralPath)shape).lineTo(5.3935747, 10.941178);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_31;
g.setTransform(defaultTransform__0_31);
g.setClip(clip__0_31);
float alpha__0_32 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_32 = g.getClip();
AffineTransform defaultTransform__0_32 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_32 is ShapeNode
paint = new LinearGradientPaint(new Point2D.Double(9.756879806518555, 12.52466106414795), new Point2D.Double(17.85013771057129, 31.674617767333984), new float[] {0.0f,1.0f}, new Color[] {new Color(224, 43, 43, 255),new Color(224, 43, 43, 0)}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(-0.9501760005950928f, 0.0f, 0.0f, -1.2505500316619873f, 51.7071418762207f, 46.10409927368164f));
shape = new GeneralPath();
((GeneralPath)shape).moveTo(43.684948, 27.784836);
((GeneralPath)shape).curveTo(48.23033, 15.493195, 39.62647, 4.2921047, 26.025827, 4.2921047);
((GeneralPath)shape).curveTo(34.595627, 4.2921047, 41.567654, 11.264062, 41.567654, 19.833925);
((GeneralPath)shape).curveTo(41.567654, 25.851019, 38.0479, 31.37442, 32.597095, 33.92194);
((GeneralPath)shape).lineTo(48.538754, 30.032932);
((GeneralPath)shape).lineTo(43.684948, 27.784836);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
origAlpha = alpha__0_32;
g.setTransform(defaultTransform__0_32);
g.setClip(clip__0_32);
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
        return 1;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 5;
    }

	/**
	 * Returns the width of the bounding box of the original SVG image.
	 * 
	 * @return The width of the bounding box of the original SVG image.
	 */
	public static int getOrigWidth() {
		return 48;
	}

	/**
	 * Returns the height of the bounding box of the original SVG image.
	 * 
	 * @return The height of the bounding box of the original SVG image.
	 */
	public static int getOrigHeight() {
		return 48;
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
	public UpdateSiteIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public UpdateSiteIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public UpdateSiteIcon(int width, int height) {
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

