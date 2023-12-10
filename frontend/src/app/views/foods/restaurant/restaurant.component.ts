import { Component, OnInit, ViewChild } from '@angular/core';
import { DxDataGridComponent } from 'devextreme-angular';
import CustomStore from 'devextreme/data/custom_store';
import { GenericService } from 'src/app/services/generic.service';
import { UtilService } from 'src/app/services/util.service';

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.scss']
})
export class RestaurantComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('dataGrid', { static: true }) dataGrid: any = DxDataGridComponent;
  events: Array<string> = [];
  restaurantService: GenericService;
  restaurantData: any;


  constructor(public service: GenericService) {
    this.restaurantService = this.service.instance('foods/restaurants');
    this.loadGrid();
  }

  ngOnInit(): void {
  }

  logEvent(eventName: any) {
    this.events.unshift(eventName);
  }

  onValueMapChanged(value: any, data: any) {
    console.log(value)
    data.setValue(value);
  }


  refreshDataGrid(e: any) {
    this.dataGrid.instance.refresh();
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.restaurantService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.items,
            totalCount: response.totalCount
          };
        });
      },

      byKey: (key) => {
        return this.restaurantService.findOne(key).then((response) => {
          return response;
        });
      },

      insert: (values) => {
        return this.restaurantService.save(values).then((response) => {
          return;
        },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      update: (key, values: any) => {
        values.id = key;
        return this.restaurantService.update(key, values).then((response) => {
          return;
        },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      },
      remove: (key) => {
        return this.restaurantService.delete(key).then((response) => {
          return;
        },
          err => {
            throw (err.error.errorMessage ? err.error.errorMessage : err.error.warningMessage);
          }
        );
      }
    });
  }
}
