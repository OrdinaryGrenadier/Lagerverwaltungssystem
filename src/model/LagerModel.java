package model;

import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class LagerModel implements TreeModel{
	private Lager root;
	public LagerModel(Lager root){
		this.root = root;
	}
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getChild(Object parent, int index) {
		if(parent instanceof Lager){
			Lager lager = (Lager) parent;
			if((lager.getChildList().size() - 1) >= index){
				Object childObjekt = lager.getChildList().get(index);
				return childObjekt;
			}
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof Lager){
				Lager lager = (Lager) parent;
				if(lager.getChildList().isEmpty()){
					return 0;
				}
				else{
					return lager.getChildList().size();
				}
		}
		return -1;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(Object node) {
		if(node instanceof Lager){
			Lager lager = (Lager) node;
			if(lager.getChildList().isEmpty()){
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}
}