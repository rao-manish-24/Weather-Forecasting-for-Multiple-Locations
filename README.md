# Weather-Forecasting-for-Multiple-Locations
Weather Forecasting System developed as part of the CSYE7374 Design Patterns course. The system implements a sophisticated Weather Bureau that manages and broadcasts weather forecasts for multiple locations, demonstrating the practical application of seven design patterns working together.

# Weather Forecasting System

A sophisticated Java application that demonstrates the implementation and interaction of seven design patterns through a simulated Weather Bureau. This system tracks and broadcasts weather forecasts for multiple locations, applies weather modeling algorithms, and simulates changing conditions over time.

## 🌟 Features

- **Multi-location Forecasting**: Generate detailed weather reports for any number of locations
- **Weather Condition Modeling**: Simulate various temperature ranges and precipitation probabilities
- **Regional Analysis**: Create composite regional forecasts by combining multiple location data
- **Algorithmic Modeling**: Apply different weather prediction algorithms to existing forecasts
- **Extensible Architecture**: Easily add new locations, conditions, or prediction models

## 🏗️ Design Patterns Implemented

This project showcases the following design patterns working together in a cohesive system:

| Pattern | Implementation | Purpose |
|---------|---------------|---------|
| **Builder** | `LocaleBuilder`, `WeatherConditionBuilder` | Constructs complex weather objects step-by-step |
| **Factory** | `WeatherEnumSingletonFactory` | Centralizes creation of weather forecast objects |
| **Singleton** | `WeatherEnumSingletonFactory` | Ensures a single factory instance throughout the application |
| **Decorator** | `WeatherDecorator`, `ModeledWeatherDecorator` | Dynamically adds conditions to existing forecasts |
| **Strategy** | `WeatherStrategyAPI` implementations | Implements different weather modeling algorithms |
| **Composite** | `CompositeLocaleAdapter` | Creates regional forecasts by combining multiple locations |
| **Adapter** | `CompositeLocaleAdapter` | Adapts weather objects to work within the composite structure |

## 🧩 Key Components

### Core Interfaces

- `WeatherAPI` - Defines temperature and precipitation properties for forecasts
- `WeatherBuilderAPI` - Common interface for all builder classes
- `WeatherFactoryAPI` - Interface for the factory that creates weather objects
- `WeatherStrategyAPI` - Defines algorithms for modifying forecasts
- `LocaleComponentAPI` - Supports the composite pattern structure

### Primary Classes

- `Locale` - Represents a location with basic weather conditions
- `WeatherDecorator` - Adds additional weather conditions to forecasts
- `ModeledWeatherDecorator` - Applies modeling strategies to weather forecasts
- `WeatherBureau` - Central entity that manages and broadcasts all forecasts
- `CompositeLocaleAdapter` - Creates regional forecasts from multiple locations
- `WeatherEnumSingletonFactory` - Singleton factory for creating weather objects

### Builder Implementations

- `LocaleBuilder` - Creates basic location objects with default conditions
- `WeatherConditionBuilder` - Adds specific weather conditions to locations
- `ModeledWeatherBuilder` - Applies weather modeling strategies to forecasts
- `RegionalForecastBuilder` - Creates composite forecasts for regions

## 🌡️ Weather Conditions Modeled

The system simulates various weather conditions through:

### Temperature Variations

- Arctic (-70°), Sub-Freezing (-40°), Freezing (-30°)
- Cold (-20°), Cool (-10°), Normal (65°)
- Warm (+10°), Hot (+20°), Heat Wave (+30°)

### Precipitation Probabilities

- Normal (0%), Mist (10%), Chance (20%)
- Slight (30%), Shower (60%), Rain (80%)

### Weather Models

- Model A: Increases temperature by 2° and precipitation by 2%
- Model B: Decreases temperature by 2° and precipitation by 2%

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java IDE (optional) - Eclipse, IntelliJ IDEA, or NetBeans

### Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/weather-forecasting-system.git
   cd weather-forecasting-system
   ```

2. Compile the project:
   ```bash
   javac edu/neu/csye7374/*.java
   ```

3. Run the application:
   ```bash
   java edu.neu.csye7374.Driver
   ```


## 📝 Example Output

When executed, the system displays detailed forecasts for all locations:

```
============Main Execution Start===================
    edu.neu.csye7374.Weathermid.demo() [version 1.9.22]...
     *** TODAY's WEATHER FORECASTS: *** 
Weather in Default Location NOW: current temp of 65 degrees and a 0% chance of precipitation (rain)
Weather in Nashua, NH NOW: current temp of 35 degrees and a 80% chance of precipitation (rain)
Weather in Boston, MA NOW: current temp of 55 degrees and a 10% chance of precipitation (rain)
Weather in Boston, MA NOW: current temp of 45 degrees and a 20% chance of precipitation (rain)
Weather in Boston, MA NOW: current temp of 35 degrees and a 30% chance of precipitation (rain)
...
    edu.neu.csye7374.Weathermid.demo() [version 1.9.22]... done!
============Main Execution End===================
```

## 🏛️ Architecture

The system follows a modular architecture:

1. **Core Weather Model** - Basic weather data representation
2. **Builder Layer** - Creates various types of weather objects
3. **Decorator Layer** - Adds conditions to existing forecasts
4. **Modeling Layer** - Applies strategies to modify forecasts
5. **Composite Layer** - Combines multiple forecasts into regional views
6. **Factory/Singleton Layer** - Controls object creation
7. **Weather Bureau** - Top-level component managing all forecasts

## 📊 Class Diagram

```
                      ┌───────────────┐
                      │ WeatherAPI    │
                      └───────┬───────┘
                              ▲
                              │
               ┌──────────────┴──────────────┐
               │                             │
      ┌────────┴────────┐          ┌─────────┴─────────┐
      │ Locale          │          │ WeatherDecoratorAPI│
      └────────┬────────┘          └─────────┬─────────┘
               │                              │
               │                     ┌────────┴─────────┐
               │                     │                  │
               │           ┌─────────┴───────┐ ┌────────┴────────┐
               │           │ WeatherDecorator│ │LocaleComponentBase│
               │           └─────────┬───────┘ └────────┬────────┘
               │                     │                  │
               │                     │          ┌───────┴────────┐
               │                     │          │                │
               │      ┌──────────────┴──┐ ┌─────┴──────┐ ┌──────┴───────┐
               │      │ModeledWeatherDec.│ │LeafLocaleA.│ │CompositeLocaleA.│
               │      └─────────────────┘ └────────────┘ └────────────────┘
               │
      ┌────────┴────────┐
      │ LocaleBuilder   │
      └─────────────────┘
```

## 🔧 Extending the System

### Adding New Locations

1. Use the `LocaleBuilder` to create new location objects:
   ```java
   WeatherAPI newLocation = new LocaleBuilder()
       .setName("Chicago, IL")
       .setTemperature(55)
       .setPrecipitation(30)
       .build();
   ```

### Creating New Weather Conditions

1. Implement custom decorators extending `WeatherDecorator`:
   ```java
   public class WindyWeatherDecorator extends WeatherDecorator {
       private int windSpeed;
       
       public WindyWeatherDecorator(WeatherAPI weather, int windSpeed) {
           super(weather);
           this.windSpeed = windSpeed;
       }
       
       @Override
       public String getDescription() {
           return super.getDescription() + " with wind speeds of " + windSpeed + " mph";
       }
   }
   ```

### Adding New Weather Models

1. Implement a new strategy implementing `WeatherStrategyAPI`:
   ```java
   public class ExtremePrecipitationStrategy implements WeatherStrategyAPI {
       @Override
       public WeatherAPI applyModel(WeatherAPI weather) {
           // Implement extreme precipitation logic
           return weather;
       }
   }
   ```

## 🤝 Contributing

Contributions are welcome! Here's how you can contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📚 Related Resources

- [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612) - The "Gang of Four" book
- [Java Design Patterns](https://java-design-patterns.com/) - Examples and explanations
- [Weather Modeling Concepts](https://www.weather.gov/jetstream/models) - Background on weather modeling

## 🙏 Acknowledgments

- Special thanks to all contributors who have helped shape this project
- Inspired by real-world weather forecasting systems and design pattern implementations
