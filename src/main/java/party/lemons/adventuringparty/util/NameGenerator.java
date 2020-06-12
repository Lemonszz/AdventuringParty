package party.lemons.adventuringparty.util;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public final class NameGenerator
{
	private static final List<String> BEGINNINGS = Lists.newArrayList(
		"No", "Ol","Lu","Lo","Av","Is","So","Ha","Ev","Wi","Ri","Th","Ch","Chr","Da","An","Jo","Ge","Ke","Br","Ed","Ro","Ti","Ja","Ry","Pa","El","Eli","Ba","Su","Je","Ka","Na","Li","Be","As","Ki","Do","Em","Mi","Ca","Am","Me","De","St","Re","La","Sh","Cy","Sa","Ma"
	);

	private static final List<String> MIDDLES = Lists.newArrayList(
		"lli","iv","jam","njam","ij","c","s","g","ph","ar","rp","el","m","be","ill","v","ch","se","om","arl","isto","n","tt","th","ev","dr","nn","or","i","wa","na","mo","ff","tric","ni","za","b","ss","r","an","ga","oro","sh","mber","che","pha"
	);

	private static final List<String> ENDINGS = Lists.newArrayList(
		"er","ma","a","lla","hia","lotte","lyn","hn","ert","rt","ael","am","id","rd","ph","as","es","les","pher","el","iel","ew","hew","ony","ny","ald","rk","ul","ven","en","rew","th","ua","hua","ge","rge","in","vin","ward","ard","ld","son","rey","ia","on","ura","uel","cca","ca","nie","rah","ssa","ol","elle","ily","nna","rly","ly","thy","tty","sa","ret","cy","ah","ica","an","ra","eth","da","fer","cia","ry","do"
	);

	private static final List<Character> VOWELS = Lists.newArrayList(
		'a', 'e', 'i', 'o', 'u'
	);

	public static String createName()
	{
		String name = BEGINNINGS.get(random.nextInt(BEGINNINGS.size()));
		if(random.nextBoolean())
		{
			name += getMiddle(!VOWELS.contains(name.charAt(name.length() - 1)));
		}
		name += getEnd(!VOWELS.contains(name.charAt(name.length() - 1)));
		return name;
	}

	private static String getMiddle(boolean vowel)
	{
		String middle = MIDDLES.get(random.nextInt(MIDDLES.size()));

		while(VOWELS.contains(middle.charAt(0)) != vowel)
		{
			middle = MIDDLES.get(random.nextInt(MIDDLES.size()));
		}
		return middle;
	}

	private static String getEnd(boolean vowl)
	{
		String end = ENDINGS.get(random.nextInt(ENDINGS.size()));

		while(VOWELS.contains(end.charAt(0)) != vowl)
		{
			end = ENDINGS.get(random.nextInt(ENDINGS.size()));
		}
		return end;
	}

	private static Random random = new Random();
}
