import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {
  ButtonGroupModule,
  ButtonModule,
  CardModule,
  CollapseModule,
  DropdownModule,
  FormModule,
  GridModule,
  NavbarModule,
  NavModule,
  SharedModule,
  UtilitiesModule
} from "@coreui/angular";
import {IconModule} from "@coreui/icons-angular";
import {ReactiveFormsModule} from "@angular/forms";
import {DocsComponentsModule} from "@docs-components/docs-components.module";
import {RouterModule, Routes} from "@angular/router";
import {DevExtremeModule} from "devextreme-angular";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {DepartmentComponent} from './department/department.component';


const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'department', component: DepartmentComponent,
        data: {
          title: 'Department'
        }
      },
    ]
  }
];

@NgModule({
  declarations: [
    DepartmentComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ButtonModule,
    ButtonGroupModule,
    GridModule,
    IconModule,
    CardModule,
    UtilitiesModule,
    DropdownModule,
    SharedModule,
    FormModule,
    ReactiveFormsModule,
    DocsComponentsModule,
    NavbarModule,
    CollapseModule,
    NavModule,
    DevExtremeModule,
    NgOptimizedImage,
    FontAwesomeModule,
  ]
})
export class DataModule {
}
