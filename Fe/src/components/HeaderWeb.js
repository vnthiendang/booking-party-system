import { Box, Button, Stack } from "@mui/material";
import React from "react";
import { logo } from "../asset";
import { useNavigate } from "react-router-dom";
import { ROUTER } from "../util";
const pages = ["Order History"];
const HeaderWeb = () => {
  const navigate = useNavigate();
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const token = sessionStorage.getItem("token")
    ? sessionStorage.getItem("token")
    : "";

  const handleCloseNavMenu = () => {
    navigate(ROUTER.ORDER_BOOKING);
  };
  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  return (
    <div
      style={{
        height: 155,
        background: "#000",
        borderBottom: "1px solid #292929",
      }}
    >
      <Stack
        direction="row"
        justifyContent={"space-between"}
        alignItems={"center"}
        style={{
          maxWidth: "1350px",
          margin: "0 auto",
          padding: "10px 0",
        }}
      >
        <img src={logo} width={128} height={128} alt="logo" />
        <Box
          sx={{
            flexGrow: 1,
            display: { xs: "none", md: "flex" },
            justifyContent: "flex-end",
          }}
        >
          {token &&
            pages.map((page) => (
              <Button
                key={page}
                onClick={handleCloseNavMenu}
                sx={{
                  my: 2,
                  color: "white",
                  display: "block",
                  fontWeight: "bold",
                  fontSize: "16px",
                }}
              >
                {page}
              </Button>
            ))}
        </Box>
        {token && (
          <Button
            variant="contained"
            sx={{
              my: 2,
              color: "white",
              display: "block",
              fontWeight: "bold",
              fontSize: "16px",
              height: "56px",
            }}
            onClick={() => sessionStorage.clear()}
          >
            Logout
          </Button>
        )}
      </Stack>
    </div>
  );
};

export default HeaderWeb;
