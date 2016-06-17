package digimorph.engine;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitSource;
import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.Multiply;

public class DMOscillator2 extends Circuit implements UnitSource {
	
	SineOscillator sine_osc1;
	SawtoothOscillatorBL saw_osc1;
	SquareOscillatorBL square_osc1;
	TriangleOscillator triangle_osc1;
	Add add1;
	Multiply multiply1;
	
	public UnitInputPort amplitude;
	public UnitInputPort frequency;
	public UnitInputPort tuning;
	public UnitOutputPort output;
	public PassThrough pass_through1;
	
	public DMOscillator2() {
		add(sine_osc1 = new SineOscillator());
		add(saw_osc1 = new SawtoothOscillatorBL());
		add(square_osc1 = new SquareOscillatorBL());
		add(triangle_osc1 = new TriangleOscillator());
		add(add1 = new Add());
		add(multiply1 = new Multiply());
		add(pass_through1 = new PassThrough());
		saw_osc1.output.connect(add1.inputA);
		
		// Expose internal ports to circuit
		addPort(amplitude = saw_osc1.amplitude);
//		addPort(frequency = saw_osc1.frequency);
		// addPort(frequency = pass_through1.input);
		addPort(frequency = multiply1.inputA);
		addPort(tuning = multiply1.inputB);
		multiply1.output.connect(pass_through1.input);
		addPort(output = add1.output);
		
		pass_through1.output.connect(sine_osc1.frequency);
		pass_through1.output.connect(saw_osc1.frequency);
		pass_through1.output.connect(square_osc1.frequency);
		pass_through1.output.connect(triangle_osc1.frequency);

		frequency.set(440); // default to A 440
		setTuning(0.5); // default to dead center
	}
	
	public UnitOutputPort getOutput() {
		return output;
	}
	
	public void setWaveform(String osc_name) {
		System.out.println(osc_name);
		switch (osc_name) {
			case "sine":
				sine_osc1.output.connect(add1.inputA);
				saw_osc1.output.disconnect(add1.inputA);
				square_osc1.output.disconnect(add1.inputA);
				triangle_osc1.output.disconnect(add1.inputA);
				break;
				
			case "saw":
				saw_osc1.output.connect(add1.inputA);
				sine_osc1.output.disconnect(add1.inputA);
				square_osc1.output.disconnect(add1.inputA);
				triangle_osc1.output.disconnect(add1.inputA);
				break;
				
			case "square":
				square_osc1.output.connect(add1.inputA);
				saw_osc1.output.disconnect(add1.inputA);
				sine_osc1.output.disconnect(add1.inputA);
				triangle_osc1.output.disconnect(add1.inputA);
				break;

			case "triangle":
				triangle_osc1.output.connect(add1.inputA);
				square_osc1.output.disconnect(add1.inputA);
				saw_osc1.output.disconnect(add1.inputA);
				sine_osc1.output.disconnect(add1.inputA);
				break;
		}
	}
	
	// range from 0 to 1 with 0 being lowest extreme and 1 being highest (not percentage)
	public void setTuning(double t) {
		// I want 440 to go from 20 to 20kHz
		// +/- 64 gets us from roughly 10 to 18000, I can live with that
		tuning.set(Math.pow(2.0, (t * 128.0 - 64.0) /12));
	}

}
