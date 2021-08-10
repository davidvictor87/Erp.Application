package erp.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import erp.application.products.Products;
import erp.application.products.repository.ProductsRepositoryImpl;

@Service
public class SortProducts {

	private ProductsRepositoryImpl productsRepository;

	@Autowired
	private SortProducts(ProductsRepositoryImpl pRepo) {
		super();
		this.productsRepository = pRepo;
	}

	@Secured(value="ROLE_ADMIN, ROLE_MANAGER, ROLE_USER")
	public List<Products> sortedProducts() {
		List<Products> sortedList = productsRepository.allValues();
		try {
            mergeSort(sortedList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sortedList;
	}
	
	private void mergeSort(List<Products> list) {
		List<Products> helperList = new ArrayList<Products>();
		mergeSort(list, helperList, 0, (list.size()-1));
	}
	
	private void mergeSort(List<Products> list, List<Products> helperList, int low, int high) {
		if(low < high) {
			int middle = (low+high)/2;
			mergeSort(list, helperList, low, middle);
			mergeSort(list, helperList, middle+1, high);
			merge(list, helperList, low, middle, high);
		}
	}
	
	private void merge(List<Products> list, List<Products> helperList, int low, int middle, int high) {
		IntStream.range(low, high).forEach(index ->{
			helperList.add(index, list.get(index));
		});
		int helperLeft = low;
		int helperRight = middle + 1;
		int current = low;
		while(helperLeft < middle && helperRight < high) {
			if(helperList.get(helperLeft).isLessThan(helperList.get(helperRight))) {
				list.set(current, helperList.get(helperLeft));
				helperLeft++;
			}else {
				list.set(current, helperList.get(helperRight));
				helperRight++;
			}
			current++;
		}
		final int remaining = middle - helperLeft;
		IntStream.range(0, remaining).forEach(index -> {
			list.set(index, helperList.get(index));
		});
	}
}
