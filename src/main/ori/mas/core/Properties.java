package ori.mas.core;

// property name => typed
// 		<type>:<name>[-min|-max]
public class Properties {

	public static final String SUFFIX_MIN = "-min";
	public static final String SUFFIX_MAX = "-max";

	/** Used for agent conceptor recognition... */
	public static final String SOUL       = "soul";

	public static final String HEALTH     = "health";
	public static final String HEALTH_MIN = HEALTH+SUFFIX_MIN;
	public static final String HEALTH_MAX = HEALTH+SUFFIX_MAX;
	public static final String FEED       = "feed";
	public static final String FEED_MIN   = FEED+SUFFIX_MIN;
	public static final String FEED_MAX   = FEED+SUFFIX_MAX;

	public static final String TARGET     = "target";


	public static final Body getTarget(Body b) {
		return (Body)(b.get(TARGET));
	}

	private Properties() { }

};

