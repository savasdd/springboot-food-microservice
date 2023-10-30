import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

declare const $: any;

const misc: any = {
  navbar_menu_visible: 0,
  active_collapse: true,
  disabled_collapse_init: 0,
};

@Injectable({
  providedIn: 'root'
})
export class UtilService {
  constructor() {
  }

  static scrollTop() {
    const elemMainPanel = <HTMLElement>document.querySelector('.main-panel');

    if (elemMainPanel) {
      elemMainPanel.scrollTop = 0;
    }
  }


  decimal2hex(d: any) {
    return (+d).toString(16).toUpperCase();
  }

  hex2decimal(hexString: any) {
    return parseInt(hexString, 16);
  }

  string2Byte(valueString: any) {
    const bytes = [];
    for (let i = 0; i < valueString.length; ++i) {
      bytes.push(valueString.charCodeAt(i));
    }
    return bytes;
  }

  minimizeSidebar() {
    const body = document.getElementsByTagName('body')[0];

    if (misc.sidebar_mini_active === true) {
      body.classList.remove('sidebar-mini');
      misc.sidebar_mini_active = false;

    } else {
      setTimeout(() => {
        body.classList.add('sidebar-mini');

        misc.sidebar_mini_active = true;
      }, 300);
    }

    // we simulate the window Resize so the charts will get updated in realtime.
    const simulateWindowResize = setInterval(() => {
      window.dispatchEvent(new Event('resize'));
    }, 180);

    // we stop the simulation of Window Resize after the animations are completed
    setTimeout(() => {
      clearInterval(simulateWindowResize);
    }, 1000);
  }

  static mergeEquallyLabeledTypes(item: any, parents: any) {
    if (item.name) {
      parents.push(item.name);
    }

    if (item.parent) {
      this.mergeEquallyLabeledTypes(item.parent, parents);
    }
    return parents.join(' / ');
  }

  getImageDimension(image: any): Observable<any> {
    return new Observable(observer => {
      const img = new Image();
      img.onload = (event) => {
        const loadedImage: any = event.currentTarget;
        image.width = loadedImage.width;
        image.height = loadedImage.height;
        observer.next(image);
        observer.complete();
      };
      img.src = image.url;
    });
  }

  timeDiffCalc(dateFuture: any, dateNow: any): string {
    let diffInMilliSeconds = Math.abs(dateFuture - dateNow) / 1000;

    // calculate days
    const days = Math.floor(diffInMilliSeconds / 86400);
    diffInMilliSeconds -= days * 86400;
    // console.log('calculated days', days);

    // calculate hours
    const hours = Math.floor(diffInMilliSeconds / 3600) % 24;
    diffInMilliSeconds -= hours * 3600;
    // console.log('calculated hours', hours);

    // calculate minutes
    const minutes = Math.floor(diffInMilliSeconds / 60) % 60;
    diffInMilliSeconds -= minutes * 60;
    // console.log('minutes', minutes);

    let difference = '';
    if (days > 0) {
      difference += (days === 1) ? `${days} day, ` : `${days} gün, `;
    }
    if (hours > 0) {
      difference += (hours === 0 || hours === 1) ? `${hours} hour, ` : `${hours} saat, `;
    }
    if (minutes > 0) {
      difference += (minutes === 0 || hours === 1) ? `${minutes} minutes` : `${minutes} dakika`;
    }
    if (difference === '') {
      difference = `${Math.round(diffInMilliSeconds)} saniye`;
    }

    return difference;
  }


  uniqueByKey(array: any, key: any) {
    array = array.filter((f: any) => f !== undefined && f !== null);
    return [...new Map(array.map((x: any) => [x[key], x])).values()];
  }

  static setPage(loadOptions: any) {
    if (!loadOptions.skip) {
      loadOptions.skip = 0;
    }
    if (!loadOptions.take) {
      loadOptions.take = 2147483647;
    }
    if (!loadOptions.requireTotalCount) {
      loadOptions.requireTotalCount = true;
    }
    return loadOptions;
  }

  static showNotification(from: any, align: any, message: string, type: string, timer: number) {
    $.notify({
      icon: 'notifications',
      message
    }, {
      type,
      timer,
      placement: {
        from,
        align
      }
    });
  }

  static getBaseURL(): string {
    return window.location.hostname;
  }

  static getFlagEmoji(countryCode: any) {
    const values = countryCode.split(/-|_/);
    countryCode = values.length > 1 ? values[1] : countryCode;
    return countryCode.toUpperCase().replace(/./g, (char: any) =>
      String.fromCodePoint(127397 + char.charCodeAt())
    );
  }

  static onInsertedUpdated(value: any, data: any): void {
    if (!data.value) {
      data.value = [value.data];
    }
    data.setValue(data.value);
  }

  static isJson(str: any) {
    if (!str) {
      return false;
    }
    let value = typeof str !== "string" ? JSON.stringify(str) : str;
    try {
      const result = JSON.parse(value);
      return (typeof result) === 'object';
    } catch (e) {
      return false;
    }
  }

}
