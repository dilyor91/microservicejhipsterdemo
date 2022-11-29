import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../timetable.test-samples';

import { TimetableFormService } from './timetable-form.service';

describe('Timetable Form Service', () => {
  let service: TimetableFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TimetableFormService);
  });

  describe('Service methods', () => {
    describe('createTimetableFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTimetableFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleUz: expect.any(Object),
            titleRu: expect.any(Object),
            titleKr: expect.any(Object),
            contentUz: expect.any(Object),
            contentRu: expect.any(Object),
            contentKr: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing ITimetable should create a new form with FormGroup', () => {
        const formGroup = service.createTimetableFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titleUz: expect.any(Object),
            titleRu: expect.any(Object),
            titleKr: expect.any(Object),
            contentUz: expect.any(Object),
            contentRu: expect.any(Object),
            contentKr: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getTimetable', () => {
      it('should return NewTimetable for default Timetable initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTimetableFormGroup(sampleWithNewData);

        const timetable = service.getTimetable(formGroup) as any;

        expect(timetable).toMatchObject(sampleWithNewData);
      });

      it('should return NewTimetable for empty Timetable initial value', () => {
        const formGroup = service.createTimetableFormGroup();

        const timetable = service.getTimetable(formGroup) as any;

        expect(timetable).toMatchObject({});
      });

      it('should return ITimetable', () => {
        const formGroup = service.createTimetableFormGroup(sampleWithRequiredData);

        const timetable = service.getTimetable(formGroup) as any;

        expect(timetable).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITimetable should not enable id FormControl', () => {
        const formGroup = service.createTimetableFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTimetable should disable id FormControl', () => {
        const formGroup = service.createTimetableFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
