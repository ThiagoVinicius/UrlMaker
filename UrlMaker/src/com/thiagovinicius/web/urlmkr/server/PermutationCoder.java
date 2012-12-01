package com.thiagovinicius.web.urlmkr.server;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
 * The implementation of this class is based on ideas shared by these gentlemen:
 *
 * - Eduardo Freire
 *   https://plus.google.com/108637584796370940979
 *
 * - Jefferson Ferreira
 *   https://plus.google.com/112265349814853561999
 *
 * - José Rogério Bezerra Barbosa Filho
 *   https://plus.google.com/114940959908787064040
 *
 * SPECIAL THANKS to Jailton Maciel, for providing a working implementation of
 * a decoder for this. In the end, I ended up using a simpler aproach, as seen
 * in this class.
 *
 *  - Jailton Maciel
 *    https://github.com/jailtonmas
 *    https://plus.google.com/104272068996010336479
 */

public class PermutationCoder implements IdCoder {

	private final List<Character> symbol_pool;

	private static final BigInteger BIG_INTEGER_FACTORIAL[] = {
		new BigInteger("1"), // 0
		new BigInteger("1"), // 1
		new BigInteger("2"), // 2
		new BigInteger("6"), // 3
		new BigInteger("24"), // 4
		new BigInteger("120"), // 5
		new BigInteger("720"), // 6
		new BigInteger("5040"), // 7
		new BigInteger("40320"), // 8
		new BigInteger("362880"), // 9
		new BigInteger("3628800"), // 10
		new BigInteger("39916800"), // 11
		new BigInteger("479001600"), // 12
		new BigInteger("6227020800"), // 13
		new BigInteger("87178291200"), // 14
		new BigInteger("1307674368000"), // 15
		new BigInteger("20922789888000"), // 16
		new BigInteger("355687428096000"), // 17
		new BigInteger("6402373705728000"), // 18
		new BigInteger("121645100408832000"), // 19
		new BigInteger("2432902008176640000"), // 20
		new BigInteger("51090942171709440000"), // 21
		new BigInteger("1124000727777607680000"), // 22
		new BigInteger("25852016738884976640000"), // 23
		new BigInteger("620448401733239439360000"), // 24
		new BigInteger("15511210043330985984000000"), // 25
		new BigInteger("403291461126605635584000000"), // 26
		new BigInteger("10888869450418352160768000000"), // 27
		new BigInteger("304888344611713860501504000000"), // 28
		new BigInteger("8841761993739701954543616000000"), // 29
		new BigInteger("265252859812191058636308480000000"), // 30
		new BigInteger("8222838654177922817725562880000000"), // 31
		new BigInteger("263130836933693530167218012160000000"), // 32
		new BigInteger("8683317618811886495518194401280000000"), // 33
		new BigInteger("295232799039604140847618609643520000000"), // 34
		new BigInteger("10333147966386144929666651337523200000000"), // 35
		new BigInteger("371993326789901217467999448150835200000000"), // 36
		new BigInteger("13763753091226345046315979581580902400000000"), // 37
		new BigInteger("523022617466601111760007224100074291200000000"), // 38
		new BigInteger("20397882081197443358640281739902897356800000000"), // 39
		new BigInteger("815915283247897734345611269596115894272000000000"), // 40
		new BigInteger("33452526613163807108170062053440751665152000000000"), // 41
		new BigInteger("1405006117752879898543142606244511569936384000000000"), // 42
		new BigInteger("60415263063373835637355132068513997507264512000000000"), // 43
		new BigInteger("2658271574788448768043625811014615890319638528000000000"), // 44
		new BigInteger("119622220865480194561963161495657715064383733760000000000"), // 45
		new BigInteger("5502622159812088949850305428800254892961651752960000000000"), // 46
		new BigInteger("258623241511168180642964355153611979969197632389120000000000"), // 47
		new BigInteger("12413915592536072670862289047373375038521486354677760000000000"), // 48
		new BigInteger("608281864034267560872252163321295376887552831379210240000000000"), // 49
		new BigInteger("30414093201713378043612608166064768844377641568960512000000000000"), // 50
		new BigInteger("1551118753287382280224243016469303211063259720016986112000000000000"), // 51
		new BigInteger("80658175170943878571660636856403766975289505440883277824000000000000"), // 52
		new BigInteger("4274883284060025564298013753389399649690343788366813724672000000000000"), // 53
		new BigInteger("230843697339241380472092742683027581083278564571807941132288000000000000"), // 54
		new BigInteger("12696403353658275925965100847566516959580321051449436762275840000000000000"), // 55
		new BigInteger("710998587804863451854045647463724949736497978881168458687447040000000000000"), // 56
		new BigInteger("40526919504877216755680601905432322134980384796226602145184481280000000000000"), // 57
		new BigInteger("2350561331282878571829474910515074683828862318181142924420699914240000000000000"), // 58
		new BigInteger("138683118545689835737939019720389406345902876772687432540821294940160000000000000"), // 59
		new BigInteger("8320987112741390144276341183223364380754172606361245952449277696409600000000000000"), // 60
		new BigInteger("507580213877224798800856812176625227226004528988036003099405939480985600000000000000"), // 61
		new BigInteger("31469973260387937525653122354950764088012280797258232192163168247821107200000000000000"), // 62
		new BigInteger("1982608315404440064116146708361898137544773690227268628106279599612729753600000000000000"), // 63
	};

	public PermutationCoder (CharSequence symbols) {
		List<Character> realSymbolPool = new LinkedList<Character>();
		for (int i = 0; i < symbols.length(); ++i) {
			realSymbolPool.add(symbols.charAt(i));
		}
		this.symbol_pool = Collections.unmodifiableList(realSymbolPool);
	}

	@Override
	public String encodeId(long id) {
		BigInteger bigId = BigInteger.valueOf(id);
		if (bigId.signum() == -1) {
			// We need a positive value
			bigId = bigId.abs().add(BigInteger.valueOf(Long.MAX_VALUE));
		}
		List<Character> available = new ArrayList<Character>(symbol_pool);
		StringBuilder result = new StringBuilder();

		bigId = bigId.mod(BIG_INTEGER_FACTORIAL[symbol_pool.size()]);
		while (available.size() > 0) {
			BigInteger split_point = BIG_INTEGER_FACTORIAL[available.size()-1];
			int level_symbol = bigId.divide(split_point).intValue();
			result.append(available.get(level_symbol));
			available.remove(level_symbol);
			bigId = bigId.mod(split_point);
		}
		return result.toString();
	}

	@Override
	public long decodeId(String encoded) {
		BigInteger result = BigInteger.ZERO;
		List<Character> available = new ArrayList<Character>(symbol_pool);
		for (int i = 0; i < encoded.length(); ++i) {
			int pos = available.indexOf(encoded.charAt(i));
			BigInteger tmp = BIG_INTEGER_FACTORIAL[encoded.length()-i-1];
			tmp = tmp.multiply(BigInteger.valueOf(pos));
			result = result.add(tmp);
			available.remove(pos);
		}
		if (result.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
			result = result.subtract(BigInteger.valueOf(Long.MAX_VALUE));
			result = result.negate();
		}
		return result.longValue();
	}

}
