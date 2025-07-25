import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorialTransaccionesComponent } from './historial-transacciones.component';

describe('HistorialTransaccionesComponent', () => {
  let component: HistorialTransaccionesComponent;
  let fixture: ComponentFixture<HistorialTransaccionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistorialTransaccionesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistorialTransaccionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
