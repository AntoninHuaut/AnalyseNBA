package project.data;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class PrepareData {
	
	private static int[] deleteIndex = new int[] {
			7, 9, 53	
	};

	private static int[] keepIndex = new int[] {
			5, 6, 7, 11, 15, 51, 56, 63, 66, 89	
	};

	private Instances instance;
	
	public PrepareData(DataSource dataSource) throws Exception {
		this.instance = dataSource.getDataSet();
		this.instance = removeData(deleteIndex, false);
		this.instance = removeData(keepIndex, true);
	}
	
	public Instances getInstances() {
		return instance;
	}
	
	private Instances removeData(int[] index, boolean keep) throws Exception {
        Remove remove = new Remove();
        remove.setAttributeIndicesArray(convertIndex(index));
        remove.setInvertSelection(keep);
        remove.setInputFormat(instance);
        Instances newData = Filter.useFilter(instance, remove);
        
        return newData;
    }
	
	private int[] convertIndex(int[] index) {
		int[] newIndex = new int[index.length];

		for (int i = 0; i < index.length; i++)
			newIndex[i] = index[i] - 1;

		return newIndex;
	}
}
