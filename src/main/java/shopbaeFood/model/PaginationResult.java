package shopbaeFood.model;

import java.util.List;

public class PaginationResult<E> {

	int currentPageNumber;
	int lastPageNumber;
	int pageSize;
	long totalRecords;
	List<E> records;

	public PaginationResult() {
	}

	public PaginationResult(int currentPageNumber, int lastPageNumber, int pageSize, long totalRecords,
			List<E> records) {
		this.currentPageNumber = currentPageNumber;
		this.lastPageNumber = lastPageNumber;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
		this.records = records;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public int getLastPageNumber() {
		return lastPageNumber;
	}

	public void setLastPageNumber(int lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<E> getRecords() {
		return records;
	}

	public void setRecords(List<E> records) {
		this.records = records;
	}

}
