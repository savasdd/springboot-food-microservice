import { Component, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { DxDataGridComponent } from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import { UtilService } from "../../../../services/util.service";
import { GenericService } from "../../../../services/generic.service";

@Component({
  selector: 'app-user-location',
  templateUrl: './user-location.component.html',
  styleUrls: ['./user-location.component.scss']
})
export class UserLocationComponent implements OnChanges {
  @Input() data: any;
  dataSource: any = {};
  departmentDataSource: any = {};
  @ViewChild('dataSourceGrid', { static: true }) dataSourceGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  userId: any = null;
  locationService: GenericService;

  constructor(private service: GenericService) {
    this.loadGrid = this.loadGrid.bind(this);
    this.locationService = this.service.instance('foods/users/locations');
    this.loadGrid();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.userId = this.data ? this.data.id : null;
  }

  logEvent(eventName: any) {
    this.events.unshift(eventName);
  }

  refreshDataGrid(e: any) {
    this.dataSourceGrid.instance.refresh();
  }

  onSelectionPersonChanged(selectedRowKeys: any, cellInfo: any, dropDownBoxComponent: any) {
    cellInfo.setValue(selectedRowKeys[0]);
    if (selectedRowKeys.length > 0) {
      dropDownBoxComponent.close();
    }
  }

  onValueMapChanged(value: any, data: any) {
    data.setValue(value);
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        loadOptions.filter = [];
        loadOptions.filter.push(['userId', '=', this.userId]);
        return this.locationService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.locationService.findOne(key).then((response: any) => {
          return response;
        });
      },

      insert: (values) => {
        values.userId = this.userId;
        return this.locationService.save(values).then((response) => {
          return;
        }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        values.userId = this.userId;
        return this.locationService.update(key, values).then((response) => {
          return;
        }
        );
      },
      remove: (key) => {
        return this.locationService.delete(key).then((response) => {
          return;
        }
        );
      }
    });
  }
}
