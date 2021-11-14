import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DebtGraphComponent } from './debt-graph.component';

describe('DebtGraphComponent', () => {
  let component: DebtGraphComponent;
  let fixture: ComponentFixture<DebtGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DebtGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DebtGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
