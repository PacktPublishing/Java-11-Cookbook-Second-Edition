public class Car{
	public Engine engine;
	public Dimensions dimensions;
	public String brandName;
	public String commercialName;
	public int modelYear;

	public Car(){}

	public Car(Engine engine, Dimensions dimensions, 
		String brandName, String commercialName, int modelYear){
		this.engine = engine;
		this.dimensions = dimensions;
		this.brandName = brandName;
		this.commercialName = commercialName;
		this.modelYear = modelYear;
	}
}