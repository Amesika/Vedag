import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DebtDashbordComponent } from './debt-dashbord.component';

describe('DebtDashbordComponent', () => {
  let component: DebtDashbordComponent;
  let fixture: ComponentFixture<DebtDashbordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DebtDashbordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DebtDashbordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
