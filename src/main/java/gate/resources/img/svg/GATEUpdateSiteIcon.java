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
public class GATEUpdateSiteIcon implements
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
origAlpha = alpha__0_6;
g.setTransform(defaultTransform__0_6);
g.setClip(clip__0_6);
float alpha__0_7 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_7 = g.getClip();
AffineTransform defaultTransform__0_7 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_7 is ShapeNode
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
origAlpha = alpha__0_9;
g.setTransform(defaultTransform__0_9);
g.setClip(clip__0_9);
float alpha__0_10 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_10 = g.getClip();
AffineTransform defaultTransform__0_10 = g.getTransform();
g.transform(new AffineTransform(0.5130434036254883f, 0.0f, 0.0f, 0.5130434036254883f, -46.06187057495117f, -104.17254638671875f));
// _0_10 is CompositeGraphicsNode
float alpha__0_10_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_10_0 = g.getClip();
AffineTransform defaultTransform__0_10_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_10_0 is ShapeNode
paint = new Color(255, 255, 255, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(162.71873, 240.47195);
((GeneralPath)shape).curveTo(162.87343, 267.3946, 118.58987, 262.11823, 118.58987, 262.11823);
((GeneralPath)shape).lineTo(118.58987, 218.82571);
((GeneralPath)shape).curveTo(118.58987, 218.82571, 162.56404, 213.54935, 162.71873, 240.47197);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.fill(shape);
paint = new Color(0, 155, 0, 255);
stroke = new BasicStroke(1.8707279f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(162.71873, 240.47195);
((GeneralPath)shape).curveTo(162.87343, 267.3946, 118.58987, 262.11823, 118.58987, 262.11823);
((GeneralPath)shape).lineTo(118.58987, 218.82571);
((GeneralPath)shape).curveTo(118.58987, 218.82571, 162.56404, 213.54935, 162.71873, 240.47197);
((GeneralPath)shape).closePath();
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_10_0;
g.setTransform(defaultTransform__0_10_0);
g.setClip(clip__0_10_0);
float alpha__0_10_1 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_10_1 = g.getClip();
AffineTransform defaultTransform__0_10_1 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_10_1 is CompositeGraphicsNode
float alpha__0_10_1_0 = origAlpha;
origAlpha = origAlpha * 1.0f;
g.setComposite(AlphaComposite.getInstance(3, origAlpha));
Shape clip__0_10_1_0 = g.getClip();
AffineTransform defaultTransform__0_10_1_0 = g.getTransform();
g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
// _0_10_1_0 is ShapeNode
paint = new Color(255, 0, 0, 255);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(153.2775, 253.50543);
((GeneralPath)shape).lineTo(135.45905, 253.50543);
((GeneralPath)shape).curveTo(131.38077, 253.50543, 128.00046, 252.15057, 125.31812, 249.44086);
((GeneralPath)shape).curveTo(122.66314, 246.73114, 121.335655, 243.3098, 121.33566, 239.17677);
((GeneralPath)shape).curveTo(121.335655, 235.07117, 122.64946, 231.75928, 125.27706, 229.24115);
((GeneralPath)shape).curveTo(127.93203, 226.72305, 131.32602, 225.46399, 135.45905, 225.46397);
((GeneralPath)shape).lineTo(151.3068, 225.46397);
((GeneralPath)shape).lineTo(151.3068, 230.34967);
((GeneralPath)shape).lineTo(135.45905, 230.34967);
((GeneralPath)shape).curveTo(132.77667, 230.34969, 130.55965, 231.21187, 128.80792, 232.93622);
((GeneralPath)shape).curveTo(127.08355, 234.6606, 126.22136, 236.87764, 126.221375, 239.58734);
((GeneralPath)shape).curveTo(126.22137, 242.2697, 127.08356, 244.4457, 128.80792, 246.1153);
((GeneralPath)shape).curveTo(130.55965, 247.78493, 132.77669, 248.61974, 135.45905, 248.61974);
((GeneralPath)shape).lineTo(148.39178, 248.61974);
((GeneralPath)shape).lineTo(148.39178, 242.50233);
((GeneralPath)shape).lineTo(135.08954, 242.50233);
((GeneralPath)shape).lineTo(135.08954, 238.02719);
((GeneralPath)shape).lineTo(153.2775, 238.02719);
((GeneralPath)shape).lineTo(153.2775, 253.50543);
g.setPaint(paint);
g.fill(shape);
paint = new Color(140, 0, 0, 255);
stroke = new BasicStroke(0.64150524f,0,0,4.0f,null,0.0f);
shape = new GeneralPath();
((GeneralPath)shape).moveTo(153.2775, 253.50543);
((GeneralPath)shape).lineTo(135.45905, 253.50543);
((GeneralPath)shape).curveTo(131.38077, 253.50543, 128.00046, 252.15057, 125.31812, 249.44086);
((GeneralPath)shape).curveTo(122.66314, 246.73114, 121.335655, 243.3098, 121.33566, 239.17677);
((GeneralPath)shape).curveTo(121.335655, 235.07117, 122.64946, 231.75928, 125.27706, 229.24115);
((GeneralPath)shape).curveTo(127.93203, 226.72305, 131.32602, 225.46399, 135.45905, 225.46397);
((GeneralPath)shape).lineTo(151.3068, 225.46397);
((GeneralPath)shape).lineTo(151.3068, 230.34967);
((GeneralPath)shape).lineTo(135.45905, 230.34967);
((GeneralPath)shape).curveTo(132.77667, 230.34969, 130.55965, 231.21187, 128.80792, 232.93622);
((GeneralPath)shape).curveTo(127.08355, 234.6606, 126.22136, 236.87764, 126.221375, 239.58734);
((GeneralPath)shape).curveTo(126.22137, 242.2697, 127.08356, 244.4457, 128.80792, 246.1153);
((GeneralPath)shape).curveTo(130.55965, 247.78493, 132.77669, 248.61974, 135.45905, 248.61974);
((GeneralPath)shape).lineTo(148.39178, 248.61974);
((GeneralPath)shape).lineTo(148.39178, 242.50233);
((GeneralPath)shape).lineTo(135.08954, 242.50233);
((GeneralPath)shape).lineTo(135.08954, 238.02719);
((GeneralPath)shape).lineTo(153.2775, 238.02719);
((GeneralPath)shape).lineTo(153.2775, 253.50543);
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
origAlpha = alpha__0_10_1_0;
g.setTransform(defaultTransform__0_10_1_0);
g.setClip(clip__0_10_1_0);
origAlpha = alpha__0_10_1;
g.setTransform(defaultTransform__0_10_1);
g.setClip(clip__0_10_1);
origAlpha = alpha__0_10;
g.setTransform(defaultTransform__0_10);
g.setClip(clip__0_10);
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
	public GATEUpdateSiteIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}
	
	/**
	 * Creates a new transcoded SVG image with the given dimensions.
	 *
	 * @param size the dimensions of the icon
	 */
	public GATEUpdateSiteIcon(Dimension size) {
	this.width = size.width;
	this.height = size.width;
	}

	public GATEUpdateSiteIcon(int width, int height) {
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

