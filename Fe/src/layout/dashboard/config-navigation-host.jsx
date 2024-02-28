import SvgColor from "../../components/svg-color";

// ----------------------------------------------------------------------

const icon = (name) => (
  <SvgColor
    src={`/assets/icons/navbar/${name}.svg`}
    sx={{ width: 1, height: 1 }}
  />
);

const navConfigHost = [
  {
    title: "Package",
    path: "/package",
    icon: icon("ic_cart"),
  },
  {
    title: "Revenue",
    path: "/revenue",
    icon: icon("ic_blog"),
  },
  {
    title: "Booking",
    path: "/booking",
    icon: icon("ic_analytics"),
  },
];

export default navConfigHost;
