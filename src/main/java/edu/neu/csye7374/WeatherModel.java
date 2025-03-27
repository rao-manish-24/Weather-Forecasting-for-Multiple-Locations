package edu.neu.csye7374;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Iterator;
import java.util.List;

/**
 * 100 TOTAL POINTS
 * WeatherModel Midterm SKELETON
 *
 * Complete the coding in this class.
 *
 * Use the following Design Patterns:
 *  Adapter, Builder, Composite, Decorator, Factory, Singleton, Strategy
 *
 * Model a Weather Bureau (WeatherBureau) with weather forecasts
 *  a forecast is Weather Conditions (WeatherAPI)
 *  for a locale (Locale), e.g. Boston, NY, DC, etc.
 *  Weather Bureau contain forecasts for various Locale, e.g. Boston, NY, DC, etc.
 *
 * Weather conditions are TWO specific metrics:
 * 	measurement for TEMPERATURE (degrees) AND
 * 	probability of PRECIPITATION (e.g. % chance of rain).
 *
 * Utilize one SINGLE factory class (WeatherFactoryAPI)
 *  injected with VARIOUS builder objects (WeatherBuilderAPI):
 *
 *  	20 POINTS 1. Create Locale builder for weather conditions in a place:
 *  	20 POINTS 2. Create Weather condition builder:
 *  	20 POINTS 3. Create MODELED Weather condition builder:
 *  	20 POINTS 4. Create a forecast builder (weather condition affecting a Locale):
 *  	20 POINTS 5. Create a REGIONAL forecast builder  (COMBINED weather condition at MULTIPLE Locale):
 *
 *  to create the DIFFERENT forecast objects (WeatherAPI)
 *  to initialize the Weather Bureau.
 *
 *  DETAILS FOLLOWING IN CLASS
 *
 */
public class WeatherModel {
	private static final int MAJOR_VERSION;
	private static final int MINOR_VERSION;
	private static final String VERSION;
	/**
	 * One and ONLY WeatherFactoryAPI Builder Factory
	 * use only one factory customized by using Builder objects (WeatherAPI)
	 */
	private static final WeatherFactoryAPI f;

	static {
		MAJOR_VERSION = 1;
		MINOR_VERSION = 9;
		VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + 22;

		f = WeatherSingletonEnum.INSTANCE; // Using WeatherSingletonEnum
	}

	/**
	 * ** STUDENT TODO **
	 *
	 * WeatherBureau class models broadcast of forecasts:
	 * (weather conditions, e.g. TEMPERATURE and PRECIPITATION %
	 * at various Locale)
	 */
	private static class WeatherBureau implements Runnable {
		private static final List<WeatherAPI> localeList;
		private static final WeatherBuilderAPI DEFAULT;
		private static final WeatherBuilderAPI NASHUA;
		private static final WeatherBuilderAPI BOSTONa;
		private static final WeatherBuilderAPI BOSTONb;
		private static final WeatherBuilderAPI BOSTONc;
		private static final WeatherBuilderAPI CT;
		private static final WeatherBuilderAPI NJ;
		private static final WeatherBuilderAPI NYC;
		private static final WeatherBuilderAPI NYCb;
		private static final WeatherBuilderAPI DC;
		private static final WeatherBuilderAPI DCa;
		private static final WeatherBuilderAPI NYComposite;

		/**
		 * ** STUDENT TODO **
		 *
		 * static initialization block
		 * (executed with class)
		 * used to initialize Weather Bureau
		 */
		static {
			/**
			 * ** STUDENT TODO **
			 *
			 * use Strategy pattern
			 * to create Weather Modeling Algorithms which will SPECIFICALLY affect and alter weather conditions
			 * AS FOLLOWS:
			 * 	Weather Model A: Increases Temperature by 2 degrees and increases Probability of Precipitation by 2%
			 * 	Weather Model B: Decreases Temperature by 2 degrees and Decreases Probability of Precipitation by 2%
			 */

			// Strategy Pattern: Creating Weather Model Algorithm A
			WeatherStrategyAPI weatherSystemA = weatherData -> new TempModifier(
				new PrecipModifier(weatherData, WeatherAPI.CHANCE_PRECIP_OFFSET),
				WeatherAPI.WARM_TEMP_OFFSET/5);

			// Strategy Pattern: Creating Weather Model Algorithm B
			WeatherStrategyAPI weatherSystemB = weatherData -> new TempModifier(
				new PrecipModifier(weatherData, -WeatherAPI.CHANCE_PRECIP_OFFSET),
				-WeatherAPI.WARM_TEMP_OFFSET/5);

			/**
			 * ** STUDENT TODO **
			 *
			 * Create Builder objects (WeatherBuilderAPI)
			 *  which will be used to create weather forecasts (WeatherAPI)
			 *  where a forecast is a Locale with one or more Weather Condition.
			 *
			 * 20 POINTS 1. Create Locale builder for weather conditions in a place:
			 * 	use Builder pattern to create a Locale builder,
			 *  e.g. Boston, NY etc., with DEFAULT Weather Conditions,
			 *  e.g. current temp of NORMAL_TEMP degrees and a NORMAL_PRECIP % chance of precipitation
			 */
			DEFAULT = new LocationBuilder("Default Location"); // Location Builder creates a base Location object.

			/**
			 * 20 POINTS 2. Create Weather condition builder:
			 *  (use Builder and Decorator patterns to create a Weather Condition decorator)
			 *  NOTE: Weather Conditions change the current Weather Conditions in a place (Locale).
			 */
			// Weather Condition Builders using WeatherCondBuilder (Builder pattern) and Modifiers (Decorator pattern)
			WeatherBuilderAPI freezingCondition = new WeatherCondBuilder(DEFAULT).addCondition(w -> new TempModifier(w, WeatherAPI.FREEZING_TEMP_OFFSET));
			WeatherBuilderAPI coolCondition = new WeatherCondBuilder(DEFAULT).addCondition(w -> new TempModifier(w, WeatherAPI.COOL_TEMP_OFFSET));
			WeatherBuilderAPI warmCondition = new WeatherCondBuilder(DEFAULT).addCondition(w -> new TempModifier(w, WeatherAPI.WARM_TEMP_OFFSET));
			WeatherBuilderAPI hotCondition = new WeatherCondBuilder(DEFAULT).addCondition(w -> new TempModifier(w, WeatherAPI.HOT_TEMP_OFFSET));
			WeatherBuilderAPI mistCondition = new WeatherCondBuilder(DEFAULT).addCondition(w -> new PrecipModifier(w, WeatherAPI.MIST_PRECIP_OFFSET));
			WeatherBuilderAPI slightRainCondition = new WeatherCondBuilder(DEFAULT).addCondition(w -> new PrecipModifier(w, WeatherAPI.SLIGHT_PRECIP_OFFSET));
			WeatherBuilderAPI rainCondition = new WeatherCondBuilder(DEFAULT).addCondition(w -> new PrecipModifier(w, WeatherAPI.RAIN_PRECIP_OFFSET));
			WeatherBuilderAPI showersCondition = new WeatherCondBuilder(DEFAULT).addCondition(w -> new PrecipModifier(w, WeatherAPI.SHOWER_PRECIP_OFFSET));

			/**
			 * 20 POINTS 3. Create MODELED Weather condition builder:
			 *  (use Builder, Decorator and Strategy patterns to create a Weather Condition decorator
			 *   altered by a weather model)
			 */
			// Modeled Condition Builders using ModeledWeatherBuilder (Builder, Strategy) and Modifiers (Decorator)
			WeatherBuilderAPI modeledWarmB = new ModeledWeatherBuilder(DEFAULT, weatherSystemB).addCondition(w -> new TempModifier(w, WeatherAPI.WARM_TEMP_OFFSET));
			WeatherBuilderAPI modeledHotA = new ModeledWeatherBuilder(DEFAULT, weatherSystemA).addCondition(w -> new TempModifier(w, WeatherAPI.HOT_TEMP_OFFSET));
			WeatherBuilderAPI modeledShowersA = new ModeledWeatherBuilder(DEFAULT, weatherSystemA).addCondition(w -> new PrecipModifier(w, WeatherAPI.SHOWER_PRECIP_OFFSET));
			WeatherBuilderAPI modeledSlightRainB = new ModeledWeatherBuilder(DEFAULT, weatherSystemB).addCondition(w -> new PrecipModifier(w, WeatherAPI.SLIGHT_PRECIP_OFFSET));


			/**
			 * 20 POINTS 4. Create a forecast builder (weather condition affecting a Locale):
			 *  using Builder, Decorator, Factory and Strategy patterns
			 *  to create a Locale affected with one or more weather conditions
			 */


			/**
			 * 20 POINTS 5. Create a REGIONAL forecast builder  (COMBINED weather condition at MULTIPLE Locale):
			 *  an AVERAGE weather condition from MULTIPLE Locale
			 *  using Adapter, Builder, Composite, Decorator, Factory, Strategy patterns
			 *  to create a COMPOSITE of multiple Locale
			 *  each affected with one or more Weather Conditions
			 *
			 *
			 * 1. Simulate forecast: Start with locale at NORMAL TEMP and NORMAL PRECIPITATION
			 * 2. AND ADD to Locale additional weather conditions as specified below
			 *	OR
			 * 1. Simulate changing forecast: Use a PREVIOUS forecast
			 * 2. AND ADD additional weather conditions as specified below
			 *	OR
			 * 1. Simulate MODELED forecast using a weather Model A or B: Start with locale at NORMAL TEMP and NORMAL PRECIPITATION
			 * 2. AND ADD additional weather conditions ALTERED by Weather Model as specified below
			 *	OR
			 * 1. Simulate multi-locale REGIONAL forecast using a Combination of PREVIOUS forecast at multiple Local
			 */

			/**
			 * Forecast #1: Nashua + FREEZING TEMPS and RAIN...
			 */
			NASHUA = new LocationForecastBuilder(new LocationBuilder("Nashua")) // Location Forecast Builder (Builder Pattern)
					.addConditionBuilder(freezingCondition)
					.addConditionBuilder(rainCondition);

			/**
			 * Boston has degrading weather conditions (each successive forecast adds weather conditions to previous forecast)
			 */
			/**
			 * Forecast #2: Boston + COOL TEMPS and MIST...
			 */
			BOSTONa = new LocationForecastBuilder(new LocationBuilder("Boston")) // Location Forecast Builder (Builder Pattern)
					.addConditionBuilder(coolCondition)
					.addConditionBuilder(mistCondition);

			/**
			 * Forecast #3: Boston + COOL TEMPS and MIST...
			 */
			BOSTONb = new LocationForecastBuilder(BOSTONa) // Location Forecast Builder (Builder Pattern) - Reusing previous forecast
					.addConditionBuilder(coolCondition)
					.addConditionBuilder(mistCondition);

			/**
			 * Forecast #4: Boston #3 + COOL TEMPS and MIST...
			 */
			BOSTONc = new LocationForecastBuilder(BOSTONb) // Location Forecast Builder (Builder Pattern) - Reusing previous forecast
					.addConditionBuilder(coolCondition)
					.addConditionBuilder(mistCondition);

			/**
			 * Forecast #5: Connecticut + COOL TEMPS and SLIGHT rain...
			 */
			CT = new LocationForecastBuilder(new LocationBuilder("Connecticut")) // Location Forecast Builder (Builder Pattern)
					.addConditionBuilder(coolCondition)
					.addConditionBuilder(slightRainCondition);

			/**
			 *  Forecast #6: New Jersey + HOT TEMPS and SLIGHT rain...
			 */
			NJ = new LocationForecastBuilder(new LocationBuilder("New Jersey")) // Location Forecast Builder (Builder Pattern)
					.addConditionBuilder(hotCondition)
					.addConditionBuilder(slightRainCondition);

			/**
			 * Forecast #7: New York + WARM TEMPS and SLIGHT rain...
			 */
			NYC = new LocationForecastBuilder(new LocationBuilder("New York")) // Location Forecast Builder (Builder Pattern)
					.addConditionBuilder(warmCondition)
					.addConditionBuilder(slightRainCondition);
			/**
			 * Forecast #8 MODELED from Model B (weatherSystemB): New York + WARM TEMPS and SLIGHT rain...
			 */
			NYCb = new LocationForecastBuilder(new LocationBuilder("New York (Modeled B)")) // Location Forecast Builder (Builder Pattern)
					.addConditionBuilder(modeledWarmB) // Modeled Weather Builder used here
					.addConditionBuilder(modeledSlightRainB); // Modeled Weather Builder used here

			/**
			 * Forecast #9: DC + HOT TEMPS and SHOWERS rain...
			 */
			DC = new LocationForecastBuilder(new LocationBuilder("DC")) // Location Forecast Builder (Builder Pattern)
					.addConditionBuilder(hotCondition)
					.addConditionBuilder(showersCondition);

			/**
			 * Forecast #10 MODELED from Model A (weatherSystemA): DC + HOT TEMPS and SHOWERS rain...
			 */
			DCa = new LocationForecastBuilder(new LocationBuilder("DC (Modeled A)")) // Location Forecast Builder (Builder Pattern)
					.addConditionBuilder(modeledHotA) // Modeled Weather Builder used here
					.addConditionBuilder(modeledShowersA); // Modeled Weather Builder used here

			/**
			 * Forecast #11: REGIONAL TriState CT, NJ, NYC (Forecast #5,#6 & #7) COMBINED (AVERAGED FORECAST METRICS) TRI-STATE REGIONAL FORCAST...
			 */
			NYComposite = new RegionalWeatherBuilder("Tri-State Region", // Regional Weather Builder (Builder, Composite Pattern)
					Arrays.asList(f.getObject(CT), f.getObject(NJ), f.getObject(NYC))); // Composite pattern is implemented in RegionalWeatherBuilder and CompositeLocaleAdapter

			/**
			 * GIVEN:
			 * use the one and only builder factory with different builder objects
			 * to create the weather forecast object for each locale
			 * and initialize all weather forecasts in Weather Bureau
			 */
			WeatherAPI[] initializedLocaleArray = {
					f.getObject(DEFAULT), // Factory Pattern: WeatherSingletonEnum.INSTANCE.getObject() is used to create WeatherAPI objects from builders
					f.getObject(NASHUA),
					f.getObject(BOSTONa),
					f.getObject(BOSTONb),
					f.getObject(BOSTONc),
					f.getObject(CT),
					f.getObject(NJ),
					f.getObject(NYC),
					f.getObject(NYCb),
					f.getObject(DC),
					f.getObject(DCa),
					f.getObject(NYComposite),
			};
			localeList = new ArrayList<WeatherAPI>(Arrays.asList(initializedLocaleArray));
		}

		@Override
		public void run() {
			System.out.println(this);
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();

			for (WeatherAPI locale : localeList) {
				sb.append(locale).append("\n");
			}
			return sb.toString();
		}

	}

	/**
	 * interface WeatherAPI implemented by each weather forecast for each locale
	 */
	private interface WeatherAPI {
		int ARCTIC_TEMP_OFFSET = -70;
		int SUB_FREEZING_TEMP_OFFSET = -40;
		int FREEZING_TEMP_OFFSET = -30;
		int COLD_TEMP_OFFSET = -20;
		int COOL_TEMP_OFFSET = -10;
		int NORMAL_TEMP = 65;
		int WARM_TEMP_OFFSET = 10;
		int HOT_TEMP_OFFSET = 20;
		int HEAT_WAVE_TEMP_OFFSET = 30;
		int NORMAL_PRECIP = 0;
		int MIST_PRECIP_OFFSET = 10;
		int CHANCE_PRECIP_OFFSET = 20;
		int SLIGHT_PRECIP_OFFSET = 30;
		int SHOWER_PRECIP_OFFSET = 60;
		int RAIN_PRECIP_OFFSET = 80;
		DecimalFormat fmt = new DecimalFormat("##.##");

		String getName();
		int getTemperature(); // Changed from double to int
		int getProbabilityPrecipitation(); // Changed from double to int

		default String weatherToString() {
			StringBuilder sb = new StringBuilder("Weather in ");

			sb.append(getName());
			sb.append(" NOW: current temp of ").append(getTemperature()).append(" degrees");
			sb.append(" and a ").append(getProbabilityPrecipitation()).append("% chance of precipitation (rain)");

			return sb.toString();
		}
	}
	/**
	 * ** STUDENT TODO **
	 *
	 * interface WeatherBuilderAPI
	 */
	private interface WeatherBuilderAPI {
		WeatherAPI build();
		WeatherBuilderAPI addCondition(ConditionApplier conditionApplier);
		WeatherBuilderAPI addConditionBuilder(WeatherBuilderAPI builder);
	}
	/**
	 * ** STUDENT TODO **
	 *
	 * interface WeatherFactoryAPI
	 */
	private interface WeatherFactoryAPI {
		WeatherAPI getObject(WeatherBuilderAPI builder);
	}

	/**
	 * ** STUDENT TODO **
	 *
	 * class WeatherBuilderFactory - Singleton and Factory Pattern
	 */
	private static class WeatherFactoryImpl implements WeatherFactoryAPI {
		// No need for WeatherFactoryImpl anymore as we are using Enum Singleton
		@Override
		public WeatherAPI getObject(WeatherBuilderAPI builder) {
			return builder.build();
		}
	}

	/**
	 * ** STUDENT TODO **
	 *
	 * WeatherSingletonEnum - Singleton and Factory Pattern using Enum
	 */
	private enum WeatherSingletonEnum implements WeatherFactoryAPI { // Enum Singleton Factory (Singleton and Factory Pattern)
		INSTANCE; // Singleton instance

		private final WeatherFactoryAPI factoryImpl = new WeatherFactoryImpl(); // Internal Factory implementation

		@Override
		public WeatherAPI getObject(WeatherBuilderAPI builder) { // Factory method to create WeatherAPI objects
			return factoryImpl.getObject(builder);
		}
	}


	/**
	 * ** STUDENT TODO **
	 *
	 * class WeatherDecoratorAPI - Abstract Decorator
	 */
	private static abstract class WeatherDecoratorAPI implements WeatherAPI { 
		protected WeatherAPI wrappedWeather;

		public WeatherDecoratorAPI(WeatherAPI wrappedWeather) { // Constructor for Decorator
			this.wrappedWeather = wrappedWeather;
		}

		@Override
		public String getName() {
			return wrappedWeather.getName();
		}

		@Override
		public int getTemperature() {
			return wrappedWeather.getTemperature();
		}

		@Override
		public int getProbabilityPrecipitation() {
			return wrappedWeather.getProbabilityPrecipitation();
		}

		@Override
		public String toString() {
			return weatherToString();
		}
	}

	/**
	 * ** STUDENT TODO **
	 *
	 * Location class represents a weather location - Base Component
	 */
	private static class Location implements WeatherAPI {
		private String name;
		private int temperature; 
		private int probabilityPrecipitation; 

		public Location(String name) {
			this.name = name;
			this.temperature = NORMAL_TEMP;
			this.probabilityPrecipitation = NORMAL_PRECIP;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getTemperature() {
			return temperature;
		}

		@Override
		public int getProbabilityPrecipitation() {
			return probabilityPrecipitation;
		}

		public void setTemperature(int temperature) { 
			this.temperature = temperature;
		}

		public void setProbabilityPrecipitation(int probabilityPrecipitation) { 
			this.probabilityPrecipitation = probabilityPrecipitation;
		}
	}
	/**
	 * ** STUDENT TODO **
	 *
	 * WeatherDecorator class represents a compounding weather condition - Concrete Decorators
	 */
	private static class TempModifier extends WeatherDecoratorAPI { 
		private int temperatureChange; // Changed from double to int

		public TempModifier(WeatherAPI weatherAPI, int temperatureChange) { 
			super(weatherAPI);
			this.temperatureChange = temperatureChange;
		}

		@Override
		public int getTemperature() {
			return super.getTemperature() + temperatureChange;
		}
	}
	private static class PrecipModifier extends WeatherDecoratorAPI { 
		private int rainfallChange; 

		public PrecipModifier(WeatherAPI weatherAPI, int rainfallChange) {
			super(weatherAPI);
			this.rainfallChange = rainfallChange;
		}

		@Override
		public int getProbabilityPrecipitation() {
			// Ensure probability is between 0 and 100%
			return Math.max(0, Math.min(100, super.getProbabilityPrecipitation() + rainfallChange));
		}
	}
	/**
	 * ** STUDENT TODO **
	 *
	 *   interface WeatherStrategyAPI - Strategy Pattern
	 */

	private interface WeatherStrategyAPI { // Strategy Interface (Strategy Pattern)
		WeatherAPI apply(WeatherAPI weather);
	}
	/**
	 * ** STUDENT TODO **
	 *
	 *  interface LocaleComponentAPI - Composite Pattern
	 */
	private interface LocaleComponentAPI extends WeatherAPI { 
		int getSize();
		void add(LocaleComponentAPI component);
		void remove(LocaleComponentAPI component);
		List<LocaleComponentAPI> getChildren();
	}

	/**
	 * ** STUDENT TODO **
	 *
	 * class LocaleComponentBase - Base for Composite Pattern
	 */
	private static abstract class LocaleComponentBase implements LocaleComponentAPI { 
		private String name;

		public LocaleComponentBase(String name) {
			this.name = name;
		}
		@Override
		public String getName() {
			return name;
		}
		@Override
		public int getSize() {
			return 1;
		}
		@Override
		public void add(LocaleComponentAPI component) {
			throw new UnsupportedOperationException("Cannot add to a leaf node");
		}
		@Override
		public void remove(LocaleComponentAPI component) {
			throw new UnsupportedOperationException("Cannot remove from a leaf node");
		}
		@Override
		public List<LocaleComponentAPI> getChildren() {
			return new ArrayList<>();
		}
		@Override
		public int getTemperature() {
			return 0; // Default, will be overridden in Leaf and Composite
		}
		@Override
		public int getProbabilityPrecipitation() {
			return 0; // Default, will be overridden in Leaf and Composite
		}
	}
	/**
	 * ** STUDENT TODO **
	 *
	 * class LeafLocaleAdapter - Adapter Pattern, Leaf in Composite
	 */
	private static class LeafLocaleAdapter extends LocaleComponentBase {
		private WeatherAPI weatherData;

		public LeafLocaleAdapter(WeatherAPI weatherData) {
			super(weatherData.getName());
			this.weatherData = weatherData;
		}

		@Override
		public int getTemperature() {
			return weatherData.getTemperature();
		}

		@Override
		public int getProbabilityPrecipitation() {
			return weatherData.getProbabilityPrecipitation();
		}

		@Override
		public String toString() {
			return weatherToString();
		}
	}

	/**
	 * ** STUDENT TODO **
	 *
	 * class CompositeLocaleAdapter - Composite in Composite Pattern
	 */
	private static class CompositeLocaleAdapter extends LocaleComponentBase { // 
		private List<LocaleComponentAPI> locationMembers = new ArrayList<>();

		public CompositeLocaleAdapter(String name) {
			super(name);
		}

		@Override
		public int getSize() {
			int size = 1;
			for (LocaleComponentAPI member : locationMembers) {
				size += member.getSize();
			}
			return size;
		}

		@Override
		public void add(LocaleComponentAPI component) {
			locationMembers.add(component);
		}

		@Override
		public void remove(LocaleComponentAPI component) {
			locationMembers.remove(component);
		}

		@Override
		public List<LocaleComponentAPI> getChildren() {
			return locationMembers;
		}

		@Override
		public int getTemperature() {
			if (locationMembers.isEmpty()) return 0;
			int totalTemp = 0;
			for (LocaleComponentAPI member : locationMembers) {
				totalTemp += member.getTemperature();
			}
			return totalTemp / locationMembers.size(); // Average Temperature
		}

		@Override
		public int getProbabilityPrecipitation() {
			if (locationMembers.isEmpty()) return 0;
			int totalPrecip = 0;
			for (LocaleComponentAPI member : locationMembers) {
				totalPrecip += member.getProbabilityPrecipitation();
			}
			return totalPrecip / locationMembers.size(); // Average Precipitation
		}

		@Override
		public String toString() {
			return weatherToString();
		}
	}

	private interface ConditionApplier {
		WeatherAPI apply(WeatherAPI weather);
	}

	private static abstract class BaseWeatherBuilder implements WeatherBuilderAPI { // Abstract base builder
		protected WeatherAPI weather;
		protected List<ConditionApplier> weatherModifications = new ArrayList<>();
		protected WeatherStrategyAPI strategy;

		public BaseWeatherBuilder(String locationName) {
			this.weather = new Location(locationName);
		}
		public BaseWeatherBuilder(WeatherBuilderAPI builder) {
			this.weather = builder.build();
		}

		public BaseWeatherBuilder(WeatherAPI existingWeather) {
			this.weather = existingWeather;
		}


		@Override
		public WeatherBuilderAPI addCondition(ConditionApplier conditionApplier) {
			this.weatherModifications.add(conditionApplier);
			return this;
		}

		@Override
		public WeatherBuilderAPI addConditionBuilder(WeatherBuilderAPI builder) {
			// Not needed for all builders, but included in interface.
			return this;
		}

		@Override
		public WeatherAPI build() {
			WeatherAPI current = this.weather;
			for (ConditionApplier modification : weatherModifications) {
				current = modification.apply(current);
			}
			if (strategy != null) {
				current = strategy.apply(current);
			}
			return current;
		}
	}

	/**
	 * LocationBuilder - Concrete Builder for Location
	 */
	private static class LocationBuilder extends BaseWeatherBuilder { // Concrete Location Builder (Builder Pattern)
		public LocationBuilder(String locationName) {
			super(locationName);
		}
		@Override
		public WeatherAPI build() {
			return super.build();
		}
	}

	/**
	 * WeatherCondBuilder - Concrete Builder for Weather Conditions (Decorators)
	 */
	private static class WeatherCondBuilder extends BaseWeatherBuilder { // Concrete Weather Condition Builder (Builder Pattern)
		public WeatherCondBuilder(WeatherBuilderAPI builder) {
			super(builder);
		}
		@Override
		public WeatherAPI build() {
			return super.build();
		}
	}

	/**
	 * ModeledWeatherBuilder - Concrete Builder for Modeled Weather Conditions (Strategy + Decorator)
	 */
	private static class ModeledWeatherBuilder extends BaseWeatherBuilder { // Concrete Modeled Weather Builder (Builder + Strategy Pattern)
		public ModeledWeatherBuilder(WeatherBuilderAPI builder, WeatherStrategyAPI strategy) {
			super(builder);
			this.strategy = strategy;
		}
		public ModeledWeatherBuilder(String locationName, WeatherStrategyAPI strategy) {
			super(locationName);
			this.strategy = strategy;
		}
		@Override
		public WeatherAPI build() {
			return super.build();
		}
	}

	/**
	 * LocationForecastBuilder - Concrete Builder for Forecasts
	 */
	private static class LocationForecastBuilder extends BaseWeatherBuilder { // Concrete Location Forecast Builder (Builder Pattern)
		public LocationForecastBuilder(WeatherBuilderAPI builder) {
			super(builder);
		}
		public LocationForecastBuilder(String locationName) {
			super(locationName);
		}
		@Override
		public WeatherBuilderAPI addConditionBuilder(WeatherBuilderAPI conditionBuilder) {
			this.weatherModifications.addAll(((BaseWeatherBuilder)conditionBuilder).weatherModifications);
			return this;
		}
		@Override
		public WeatherAPI build() {
			return super.build();
		}
	}

	/**
	 * RegionalWeatherBuilder - Concrete Builder for Regional Forecasts (Composite)
	 */
	private static class RegionalWeatherBuilder implements WeatherBuilderAPI { // Concrete Regional Weather Builder (Builder + Composite Pattern)
		private String regionName;
		private List<WeatherAPI> regionForecasts;

		public RegionalWeatherBuilder(String regionName, List<WeatherAPI> regionForecasts) {
			this.regionName = regionName;
			this.regionForecasts = regionForecasts;
		}
		@Override
		public WeatherAPI build() {
			CompositeLocaleAdapter composite = new CompositeLocaleAdapter(regionName); // Changed from RegionCompositeAdapter to CompositeLocaleAdapter
			for (WeatherAPI forecast : regionForecasts) {
				composite.add(new LeafLocaleAdapter(forecast)); // Changed from SingleLocationAdapter to LeafLocaleAdapter
			}
			return composite;
		}
		@Override
		public WeatherBuilderAPI addCondition(ConditionApplier conditionApplier) {
			// Not applicable for RegionalWeatherBuilder
			return this;
		}
		@Override
		public WeatherBuilderAPI addConditionBuilder(WeatherBuilderAPI builder) {
			// Not applicable for RegionalWeatherBuilder
			return this;
		}
	}

	/**
	 * GIVEN:
	 *
	 * demonstrate the use of this class
	 */
	public static void demo() {
		System.out.println("\n\t" + WeatherModel.class.getName() + ".demo() [version " + VERSION + "]...");

		System.out.println("\n\t *** TODAY's WEATHER FORECASTS: *** \n");

		new WeatherBureau().run();

		System.out.println("\n\t" + WeatherModel.class.getName() + ".demo() [version " + VERSION + "]... done!");
	}
}