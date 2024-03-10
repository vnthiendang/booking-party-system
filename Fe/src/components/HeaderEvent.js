import { Box, Button, Container, Stack } from "@mui/material";
import { logo } from "../asset";
import React from "react";
import { useNavigate } from "react-router-dom";

const HeaderEvent = ({ showBtn }) => {
  const navigate = useNavigate();
  return (
    <Box
      sx={{
        background: "#f1f1f1",
        borderBottom: "1px solid #d7d7d7",
        height: "59px",
        paddingTop: "10px",
      }}
    >
      <Container
        maxWidth="lg"
        sx={{
          display: "flex",
          justifyContent: "space-between",
        }}
      >
        <Stack direction="row" alignItems="center">
          <img
            src={logo}
            alt="logo"
            width={36}
            height={36}
            onClick={() => navigate("/")}
          />
          <h3
            style={{
              marginLeft: "5px",
            }}
            onClick={() => navigate("/")}
          >
            Booking Event
          </h3>
        </Stack>
        {showBtn && (
          <Button
            variant="contained"
            onClick={() => {
              sessionStorage.clear();
              navigate("/");
            }}
          >
            Log out
          </Button>
        )}
      </Container>
    </Box>
  );
};

export default HeaderEvent;
