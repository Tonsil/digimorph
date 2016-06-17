package digimorph.engine;

import javax.swing.JApplet;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.EnvelopeDAHDSR;
// import com.jsyn.unitgen.Add;
import digimorph.engine.DMFilter;

// import com.jsyn.unitgen.FilterLowPass;
// import com.jsyn.unitgen.FilterStateVariable;

public class DMMain extends JApplet {
	
	private static final long serialVersionUID = -2704222221111608377L;
	private Synthesizer synth;
	DMOscillator2 osc1;
	DMOscillator2 osc2;
	TriangleOscillator lfo1;
	TriangleOscillator lfo2;
	SquareOscillator pulse1;
	DMFilter filter1;
	LineOut lineOut;
	EnvelopeDAHDSR envelope1;
	
	public void init() {
		synth = JSyn.createSynthesizer();
		// Add a tone generator.
		synth.add(osc1 = new DMOscillator2());
		synth.add(osc2 = new DMOscillator2());
		
		synth.add(lfo1 = new TriangleOscillator());
		synth.add(lfo2 = new TriangleOscillator());
		
		synth.add(filter1 = new DMFilter());
		
		synth.add(envelope1 = new EnvelopeDAHDSR());
		
		// Add an output mixer.
		synth.add( lineOut = new LineOut() );
		
		synth.add(pulse1 = new SquareOscillator());
		
		osc1.output.connect(filter1.input);
		osc2.output.connect(filter1.input);

		// filter1.amplitude.setup( 0.0, 1.0, 10.0 );
		filter1.frequency.set(600);
		filter1.resonance.set(0.5);
		filter1.output.connect( 0, lineOut.input, 0 );
		filter1.output.connect( 0, lineOut.input, 1 );
		
		osc1.amplitude.setup( 0.0, 1.0, 10.0 );
		osc2.amplitude.setup( 0.0, 1.0, 10.0 );
		osc1.frequency.set(309);
		osc2.frequency.set(262);
		lfo1.amplitude.set(1); // this amplitude would be the filter control for how much the external mod. affects
		lfo1.frequency.set(1);
		lfo2.frequency.set(1000);

		lfo1.output.connect(filter1.fcv);
		filter1.fcv_amp.set(250);
		
		// trying an envelope on for size
		pulse1.output.connect(envelope1.input);
		pulse1.frequency.setup( 0.001, 3, 10.0 );
	    envelope1.attack.set(.01);
	    envelope1.decay.set(2);
	    envelope1.sustain.set(.5);
	    envelope1.output.connect(filter1.amplitude);
	    playScale();
	}
	
	public void start() {
		// Start synthesizer using default stereo output at 44100 Hz.
		// lfo1.start();
		synth.start();
		// We only need to start the LineOut. It will pull data from the oscillator.
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
	
	public void setRate(double rate) {
		pulse1.frequency.set(rate);
	}

	public void setOsc1Freq(double freq) {
		osc1.frequency.set(freq);
	}
	
	public void setOsc1Waveform(String name) {
		osc1.setWaveform(name);
	}
	
	public void setOsc2Freq(double freq) {
		osc2.frequency.set(freq);
	}

	public void setOsc2Waveform(String name) {
		osc2.setWaveform(name);
	}

	public void setEnv1Attack(double attack) {
		envelope1.attack.set(attack);
	}

	public void setEnv1Decay(double decay) {
		envelope1.decay.set(decay);
	}

	public void setEnv1Sustain(double sustain) {
		envelope1.sustain.set(sustain);		
	}

	public void setEnv1Release(double release) {
		envelope1.release.set(release);
	}
	
	public void setFilter1Freq(double freq) {
		filter1.frequency.set(freq);
	}

	public void setFilter1Resonance(double resonance) {
		filter1.resonance.set(resonance);
	}
	
	public void setFilter1FCV(double v) {
		filter1.fcv_amp.set(v);
	}
	
	public void playScale() {
		// From : http://www.phy.mtu.edu/~suits/notefreqs.html
		
		//		C0 	        16.35
		//		C#0/Db0 	17.32
		//		D0 	        18.35
		//		D#0/Eb0 	19.45
		//		E0          20.60
		//		F0          21.83
		//		F#0/Gb0 	23.12
		//		G0 	24.50
		//		G#0/Ab0 	25.96
		//		A0 	27.50
		//		A#0/Bb0 	29.14
		//		B0          30.87
		//		C1          32.70		
		double step_time = 0.25;
		double now_time = synth.getCurrentTime() + .2;
		osc1.frequency.set(262.0, now_time);
		now_time += step_time;
		
		osc1.frequency.set(294.0, now_time);
		now_time += step_time;

		osc1.frequency.set(330.0, now_time);
		now_time += step_time;

		osc1.frequency.set(349.0, now_time);
		now_time += step_time;

		osc1.frequency.set(392.0, now_time);
		now_time += step_time;

		osc1.frequency.set(440.0, now_time);
		now_time += step_time;

		osc1.frequency.set(493.0, now_time);
		now_time += step_time;

		osc1.frequency.set(523.0, now_time);
		now_time += step_time;
	}
}
