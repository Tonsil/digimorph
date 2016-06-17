package digimorph.engine;

import javax.swing.JApplet;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.Add;

public class NoSound extends JApplet {
	
	private static final long serialVersionUID = -2704222221111608377L;
	private Synthesizer synth;
	SawtoothOscillatorBL saw_osc1;
	TriangleOscillator lfo1;
	Add add1;
	FilterStateVariable lowpass_filter1;
	LineOut lineOut;
	
	public void init() {
		synth = JSyn.createSynthesizer();
		synth.add(saw_osc1 = new SawtoothOscillatorBL());
		synth.add(lfo1 = new TriangleOscillator());
		synth.add(add1 = new Add());
		synth.add(lowpass_filter1 = new FilterStateVariable()); 
		synth.add(lineOut = new LineOut());
		
		saw_osc1.output.connect(lowpass_filter1.input);

		lowpass_filter1.amplitude.setup( 0.0, 1.0, 10.0 );
		// lowpass_filter1.frequency.set(1000);
		lowpass_filter1.resonance.set(0.5);
		lowpass_filter1.output.connect( 0, lineOut.input, 0 );
		lowpass_filter1.output.connect( 0, lineOut.input, 1 );
		
		saw_osc1.amplitude.setup( 0.0, 1.0, 10.0 );
		saw_osc1.frequency.set(309);
		lfo1.amplitude.set(250); // this amplitude would be the filter control for how much the external mod. affects
		lfo1.frequency.set(1);
		// lfo1.output.connect(lowpass_filter1.frequency);
		
		// info on how to create a modulation for e.g. filter frequency:
		// http://www.softsynth.com/jsyn/tutorial/osc_control.php
		add1.output.connect(lowpass_filter1.frequency);
		lfo1.output.connect(add1.inputA); // inputA is the external modulation source
		add1.inputB.set(500); // inputB is the internal value, like the current frequency knob value
		
	}
	
	public void start() {
		synth.start();
		lineOut.start();
	}

	public void stop() {
		synth.stop();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestSine applet = new TestSine();
		JAppletFrame frame = new JAppletFrame( "Testing", applet );
		frame.setSize( 640, 400 );
		frame.setVisible( true );
		frame.test();
	}

}
