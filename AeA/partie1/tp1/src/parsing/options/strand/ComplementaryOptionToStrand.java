package parsing.options.strand;

import manager.Behavior;
import pattern.Strand;

public class ComplementaryOptionToStrand implements
		Behavior<OptionToStrandParameters, OptionToStrandResult> {

	@Override
	public boolean accept(OptionToStrandParameters parameters) {
		final String option = parameters.getOption();
		return "-comp".equals(option);
	}

	@Override
	public OptionToStrandResult execute(OptionToStrandParameters parameters) {
		final Strand strand = parameters.getStrand();
		return new OptionToStrandResult(strand.getComplementary());
	}

}
