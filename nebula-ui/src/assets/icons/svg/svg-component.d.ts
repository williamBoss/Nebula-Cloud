
declare module '~virtual/svg-component' {
  const SvgIcon: import("vue").DefineComponent<{
      name: {
          type: import("vue").PropType<"icon-dept" | "icon-exit" | "icon-header" | "icon-homePage" | "icon-menu" | "icon-role" | "icon-settings">;
          default: string;
          required: true;
      };
  }, {}, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, {}, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<import("vue").ExtractPropTypes<{
      name: {
          type: import("vue").PropType<"icon-dept" | "icon-exit" | "icon-header" | "icon-homePage" | "icon-menu" | "icon-role" | "icon-settings">;
          default: string;
          required: true;
      };
  }>>, {
      name: "icon-dept" | "icon-exit" | "icon-header" | "icon-homePage" | "icon-menu" | "icon-role" | "icon-settings";
  }>;
  export const svgNames: ["icon-dept" , "icon-exit" , "icon-header" , "icon-homePage" , "icon-menu" , "icon-role" , "icon-settings"];
  export type SvgName = "icon-dept" | "icon-exit" | "icon-header" | "icon-homePage" | "icon-menu" | "icon-role" | "icon-settings";
  export default SvgIcon;
}
