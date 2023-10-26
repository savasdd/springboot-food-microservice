import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthUserComponent } from './auth-user.component';

describe('AuthUserComponent', () => {
  let component: AuthUserComponent;
  let fixture: ComponentFixture<AuthUserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuthUserComponent]
    });
    fixture = TestBed.createComponent(AuthUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
