package shopbaeFood.utils;

import java.util.List;

public class Page<E> {
	private int lastPageNumber;
	private List<E> paging;

	public Page() {
	}

	public Page(int lastPageNumber, List<E> paging) {
		this.lastPageNumber = lastPageNumber;
		this.paging = paging;
	}

	public int getLastPageNumber() {
		return lastPageNumber;
	}

	public void setLastPageNumber(int lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	public List<E> getPaging() {
		return paging;
	}

	public void setPaging(List<E> paging) {
		this.paging = paging;
	}

	public List<E> paging(int page, int pageSize, List<E> list) {
		page = page - 1;
		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, list.size());
		List<E> currentPageProduct = list.subList(fromIndex, toIndex);
		return currentPageProduct;
	}

	public int lastPageNumber(int pageSize, List<E> list) {

		int lastPageNumber = (list.size() / pageSize);
		if (list.size() % pageSize != 0) {
			lastPageNumber = (list.size() / pageSize) + 1;
		}
		return lastPageNumber;
	}

}
