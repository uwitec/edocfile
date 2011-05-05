if (document.getElementById) {
	var tree = new WebFXTree('Root');
	tree.setBehavior('classic');
	var a = new WebFXLoadTreeItem('1','fileAction!createTreeMenu.action?parentId=0&Rnd=' + Math.random());
	//var a = new WebFXLoadTreeItem('1','tree.xml');
	tree.add(a);
	
	var d = new WebFXTreeItem('2');
	tree.add(d);
	var e = new WebFXTreeItem('2.1');
	d.add(e);
	e.add(new WebFXTreeItem('2.1.1'));
	e.add(new WebFXTreeItem('2.1.2'));
	e.add(new WebFXTreeItem('2.1.3'));
	d.add(new WebFXTreeItem('2.2'));
	d.add(new WebFXTreeItem('2.3'));
	d.add(new WebFXTreeItem('2.4'));
	//document.write(tree);
	tree.write();
}
