package erp.application.entities;

public class AsynchrousHandler {
	
	private EventStarter eventStarter;
	
	public void registerEventHandler(EventStarter eStarter) {
		this.eventStarter = eStarter;
	}
	
	public void executeAsyncTask(int id, String product_category, String product_manufacturer, String product_name, int vat_level, int product_price, String product_code) {
		new Thread(new Runnable() {
			public void run() {
				if(eventStarter != null) {
					eventStarter.eventStarter(id, product_category, product_manufacturer, product_name, vat_level, product_price, product_code);
				}
			}
		}).start();
	}

}
