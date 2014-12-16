Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', 'Resources/ux/');
Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.form.*',
    'Ext.ux.form.SearchField'
]);
Ext.application({
    name   : 'MyApp',
    launch : function() {

    	Ext.create('Ext.container.Viewport', {
    	    layout: 'border',
    	    items: [{
                region: 'west',
                title: 'Navigation',
                width: 200,
                split: true,
                collapsible: true,
                floatable: false
            }, {
                region: 'center',
                xtype: 'tabpanel',
                items: [getFoodListGrid(), 
                        {
                    title: 'Another Tab',
                    html: 'Hello world 2'
                }, {
                    title: 'Closable Tab',
                    html: 'Hello world 3',
                    closable: true
                }]
            }]
    	});

    }
});

function grid(){
	return '<p>Window2 configured with:</p><pre style="margin-left:20px"><code>header: {\n    titlePosition: 2,\n    titleAlign: "center"\n},\nmaximizable: true,\ntools: [{type: "pin"}],\nclosable: true</code></pre>';
}



