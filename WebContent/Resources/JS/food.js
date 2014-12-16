

function getFoodListGrid(){
    Ext.define('ForumThread', {
        extend: 'Ext.data.Model',
        fields: [{
            name: 'name',
            mapping: 'name'
        }, {
            name: 'type',
            mapping: 'type'
        }, {
            name: 'state',
            type: 'int'
        }
        ],
        idProperty: 'id'
    });
    
    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
        id: 'store',
        model: 'ForumThread',
        
        // The topics-remote.php script appears to be hardcoded to use 50, and ignores this parameter, so we
        // are forced to use 50 here instead of a possibly more efficient value.
        pageSize: 50,

        // This web service seems slow, so keep lots of data in the pipeline ahead!
        leadingBufferZone: 1000,
        proxy: {
            // load using script tags for cross domain, if the data in on the same domain as
            // this page, an HttpProxy would be better
            type: 'ajax',
            url: 'http://192.168.0.100:8090/EatHelper/food/getAllFood.do',
            reader: {
                rootProperty: 'foodlist',
                totalProperty: 'totalCount'
            },
            // sends single sort as multi parameter
            simpleSortMode: true,
            
            // Parameter name to send filtering information in
            filterParam: 'query',

            // The PHP script just use query=<whatever>
            encodeFilters: function(filters) {
                return filters[0].value;
            }
        },
        listeners: {
            totalcountchange: onStoreSizeChange
        },
        writer: {
            type: 'json'
        },        
        remoteFilter: true,
        autoLoad: true
    });
    
    function onStoreSizeChange() {
        grid.down('#status').update({count: store.getTotalCount()});
    }

    function renderTopic(value, p, record) {
        return Ext.String.format(
            '<a href="http://sencha.com/forum/showthread.php?p={1}" target="_blank">{0}</a>',
            value,
            record.getId()
        );
    }
    
    var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
        clicksToMoveEditor: 1,
        autoCancel: false,
        //editing:true
    })    
    
	var states = Ext.create('Ext.data.Store', {
	    fields: ['value', 'name'],
	    data : [
	        {"value":0, "name":"荤菜"},
	        {"value":1, "name":"素材"},
	        {"value":2, "name":"汤"}
	    ]
	});    
    
	var storeFoodType = Ext.create('Ext.data.Store', {
	    fields: ['value', 'name'],
	    data : [
	        {"value":0, "name":"未选"},
	        {"value":1, "name":"候选"},
	    ]
	});    
    
    var combo = Ext.create('Ext.form.ComboBox', {
        fieldLabel: 'Choose State',
        store: states,
        queryMode: 'local',
        displayField: 'name',
        valueField: 'value',
//        renderTo: Ext.getBody()
    });    

    
    var grid = Ext.create('Ext.grid.Panel', {
        width: 700,
        height: 470,
//        collapsible: true,
        plugins: [rowEditing],
        title: 'ExtJS.com - Browse Forums',
        frame: true,
        store: store,
        loadMask: true,
        dockedItems: [{
            dock: 'top',
            xtype: 'toolbar',
            items: [{
                width: 400,
                fieldLabel: 'Search',
                labelWidth: 50,
                xtype: 'searchfield',
                store: store
            },{
                text: 'Add',
                iconCls: 'icon-add',
                handler: function(){
                    // empty record
                    store.insert(store.getCount(), new ForumThread());
                    rowEditing.startEdit(store.getCount()-1, 0);
                }
            },'-', {
                itemId: 'delete',
                text: 'Delete',
                iconCls: 'icon-delete',
                handler: function(){
                	alert('delete');
                    var selection = grid.getView().getSelectionModel().getSelection()[0];
                    if (selection) {
                        store.remove(selection);
                    }
                }
            }, '->', {
                xtype: 'component',
                itemId: 'status',
                tpl: 'Matching threads: {count}',
                style: 'margin-right:5px'
            }]
        },{
            xtype: 'toolbar',
            dock: 'bottom',
            ui: 'footer',
            items: [{
                iconCls: 'icon-save',
                itemId: 'save',
                text: 'Save',
                scope: this,
                handler: function(){
                	var newRecords = store.getNewRecords();
                	var newFoods = [];
                	Ext.Array.forEach(newRecords,function(record,index,array){
                		newFoods.push(record.data);
                	});
                	var updateFoods = [];
                	var updatedRecords = store.getUpdatedRecords();
                	Ext.Array.forEach(updatedRecords,function(record,index,array){
                		updateFoods.push(record.data);
                	});
                	var removeFoods = [];
                	var removedRecords = store.getRemovedRecords();
                	Ext.Array.forEach(removedRecords,function(record,index,array){
                		removeFoods.push(record.data.id);
                	});                	
                	Ext.Ajax.request({
                	    url: 'http://192.168.0.100:8090/EatHelper/food/addFoods.do',
                	    params: {
                	    	newFoods:Ext.encode(newFoods),
                	    	updateFoods:Ext.encode(updateFoods),
                	    	removeFoods:Ext.encode(removeFoods)
                	    },
                	    success: function(response){
                	        var text = response.responseText;
                	        alert('操作成功');
                	        // process server response here
                	    }	
                	});
                }
            }]
        }
        ],
//        selModel: {
//            pruneRemoved: false
//        },
        //multiSelect: true,
        viewConfig: {
            trackOver: false,
            emptyText: '<h1 style="margin:20px">No matching results</h1>'
        },
        // grid columns
        columns:[{
            xtype: 'rownumberer',
            width: 50,
            sortable: false
        },{
            tdCls: 'x-grid-cell-topic',
            text: "name",
            dataIndex: 'name',
            editor: {
                // defaults to textfield if no xtype is supplied
                allowBlank: false
            },            
            //flex: 1,
            //renderer: renderTopic,
            sortable: false
        },{
            text: "type",
            dataIndex: 'type',
            align: 'center',
            width: 70,
            sortable: false,
            editor:{
            	xtype:'combo',
                store: states,
                queryMode: 'local',
                displayField: 'name',
                valueField: 'value'           	
            },
            renderer:function(val,cellmeta,record){
            	if(val==0){
            		return '荤菜';
            	}else if(val == 1){
            		return '素菜';
            	}else if(val ==2){
            		return '汤';
            	}
            	
            }
        
        },{
            id: 'last',
            text: "state",
            dataIndex: 'state',
            width: 130,
            sortable: false,
            editor:{
            	xtype:'combo',
                store: storeFoodType,
                queryMode: 'local',
                displayField: 'name',
                valueField: 'value'           	
            },
            renderer:function(val,cellmeta,record){
            	if(val==1){
            		return '候选';
            	}else {
            		return '未选';
            	}
            	
            }
        }]    
        //renderTo: Ext.getBody()
    });
    
    
    
    return grid;
    
}