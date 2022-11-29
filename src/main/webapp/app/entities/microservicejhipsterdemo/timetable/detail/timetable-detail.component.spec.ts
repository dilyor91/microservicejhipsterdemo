import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TimetableDetailComponent } from './timetable-detail.component';

describe('Timetable Management Detail Component', () => {
  let comp: TimetableDetailComponent;
  let fixture: ComponentFixture<TimetableDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TimetableDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ timetable: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TimetableDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TimetableDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load timetable on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.timetable).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
