import {Component, OnInit, ViewChild} from '@angular/core';
import {DxDataGridComponent} from "devextreme-angular";
import CustomStore from "devextreme/data/custom_store";
import {faRefresh, faShoppingBasket} from "@fortawesome/free-solid-svg-icons";
import {GenericService} from "../../../services/generic.service";
import {UtilService} from "../../../services/util.service";

@Component({
  selector: 'app-service-group',
  templateUrl: './auth-group.component.html',
  styleUrls: ['./auth-group.component.scss']
})
export class AuthGroupComponent implements OnInit {
  dataSource: any = {};
  @ViewChild('dataSourceGrid', {static: true}) dataSourceGrid: any = DxDataGridComponent;
  updateMod: boolean = false;
  data: any;
  groupService: GenericService;

  constructor(private service: GenericService) {
    this.groupService = this.service.instance('auths/groups');
    this.loadGrid();
  }

  ngOnInit(): void {
  }


  onEditorPreparing(e: any) {
    if (e.parentType === 'dataRow') {
      this.data = e.row.data;
      this.updateMod = e.row.data.id != undefined;
    }
  }

  refreshDataGrid(e: any) {
    this.dataSourceGrid.instance.refresh();
  }

  loadGrid() {
    this.dataSource = new CustomStore({
      key: 'id',
      load: (loadOptions) => {
        return this.groupService.findAll(UtilService.setPage(loadOptions)).then((response: any) => {
          return {
            data: response.data,
            totalCount: response.totalCount
          };
        });
      },
      insert: (values) => {
        return this.groupService.save(values).then((response) => {
            return;
          }
        );
      },
    });
  }

  protected readonly faShoppingBasket = faShoppingBasket;
  protected readonly faRefresh = faRefresh;
}
